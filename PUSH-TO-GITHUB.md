# Pousser AutoLoc vers GitHub

Dépôt cible : https://github.com/krimi-mounib/spring-boot (vérifié vide lors de la préparation du paquet).

## Méthode recommandée

1. Extraire `AutoLoc-ASI-26-27-GitHub-Ready.zip` dans un dossier local.
2. Ouvrir un terminal dans le dossier `AutoLoc-ASI-26-27` (celui qui contient `README.md`).
3. Vérifier que Git est installé : `git --version`.
4. Initialiser et committer :

   ```bash
   git init -b main
   git add .
   git commit -m "Add AutoLoc Atelier 2, 4 and 5 projects"
   ```

5. Relier le dépôt distant et pousser :

   ```bash
   git remote add origin https://github.com/krimi-mounib/spring-boot.git
   git push -u origin main
   ```

6. Si GitHub demande l’authentification, utiliser le navigateur / credential manager. Ne pas coller un Personal Access Token dans un chat ni le mettre dans les fichiers du projet.
7. Ouvrir la page du dépôt et vérifier que `atelier-2`, `atelier-4`, `atelier-5` et `README.md` sont visibles. L’action GitHub Actions lancera une vérification Maven pour chaque projet.

## Si vous préférez ne pas utiliser la ligne de commande

Créez une branche `main` dans le dépôt, puis téléversez les fichiers extraits depuis l’interface GitHub. La ligne de commande reste préférable pour conserver correctement les fichiers Maven et l’historique.

## Si `git push` est rejeté

Comme le dépôt a été vide lors de la vérification, cela ne devrait pas arriver sauf si vous ou un autre outil y avez poussé entre-temps. Dans ce cas, ne forcez pas le push : faites `git fetch origin` puis examinez `git log --oneline --decorate --all` avant de réconcilier les historiques.
