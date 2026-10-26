#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8081/api}"

command -v curl >/dev/null || { echo "curl est requis." >&2; exit 1; }
command -v python3 >/dev/null || { echo "python3 est requis pour lire le JSON." >&2; exit 1; }

printf '[1/5] OpenAPI... '
curl --silent --show-error --fail "$BASE_URL/v3/api-docs" >/dev/null
echo 'OK (200)'

printf '[2/5] GET réservations... '
reservations_json="$(curl --silent --show-error --fail "$BASE_URL/reservations")"
reservation_id="$(printf '%s' "$reservations_json" | python3 -c 'import json,sys; x=json.load(sys.stdin); print(x[0]["idReservation"] if x else "")')"
if [[ -z "$reservation_id" ]]; then
  echo 'Aucune réservation. Lancez l’application avec le profil demo ou créez une réservation dans Postman.' >&2
  exit 2
fi
echo "OK (200), reservationId=$reservation_id"

printf '[3/5] Simulation de prix... '
response_file="$(mktemp)"
status="$(curl --silent --show-error -o "$response_file" -w '%{http_code}' \
  -H 'Content-Type: application/json' \
  -d "{\"reservationId\":$reservation_id,\"typeClient\":\"VIP\",\"codePromo\":\"BIENVENUE\",\"modePaiement\":\"CARTE\"}" \
  "$BASE_URL/simulations-prix")"
if [[ "$status" != '200' ]]; then cat "$response_file"; rm -f "$response_file"; echo "Échec: HTTP $status" >&2; exit 1; fi
python3 - "$response_file" <<'PY'
import json,sys
with open(sys.argv[1], encoding='utf-8') as f: data=json.load(f)
assert data.get('persiste') is False, data
assert 'montantFinal' in data, data
print('OK (200), montantFinal=' + str(data['montantFinal']) + ', persiste=false')
PY
rm -f "$response_file"

printf '[4/5] Ressource introuvable... '
status="$(curl --silent --show-error -o /dev/null -w '%{http_code}' "$BASE_URL/reservations/9223372036854775807")"
[[ "$status" == '404' ]] || { echo "Échec: attendu 404, reçu $status" >&2; exit 1; }
echo 'OK (404)'

printf '[5/5] Paiement en lecture seule... '
status="$(curl --silent --show-error -o /dev/null -w '%{http_code}' -X POST \
  -H 'Content-Type: application/json' -d '{"montant":1,"modePaiement":"CARTE"}' "$BASE_URL/paiements")"
[[ "$status" == '405' ]] || { echo "Échec: attendu 405, reçu $status" >&2; exit 1; }
echo 'OK (405)'

echo 'Tous les smoke tests ont réussi.'
