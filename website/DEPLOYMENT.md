# 🚀 GitHub Pages Deployment Guide

This guide will help you deploy the Httply website to GitHub Pages.

## 📋 Prerequisites

1. **GitHub Account**: Make sure you have a GitHub account
2. **Repository**: The Httply repository should be on GitHub
3. **Node.js**: Installed on your system
4. **Git**: Installed and configured

## 🛠️ Setup Steps

### 1. Install Dependencies

```bash
cd website
npm install
```

### 2. Configure package.json

The `package.json` has been updated with:
- `homepage`: "https://tuhinx.github.io/httply"
- `predeploy`: "npm run build"
- `deploy`: "gh-pages -d build"

### 3. Build the Project

```bash
npm run build
```

### 4. Deploy to GitHub Pages

```bash
npm run deploy
```

## 🔄 Deployment Process

The deployment process will:

1. **Build**: Create optimized production build
2. **Deploy**: Push build files to `gh-pages` branch
3. **Publish**: GitHub Pages will serve the website

## 🌐 Access Your Website

After deployment, your website will be available at:
**https://tuhinx.github.io/httply**

## 📝 Manual Deployment Commands

```bash
# Build the project
npm run build

# Deploy to GitHub Pages
npm run deploy

# Or use the combined command
npm run deploy
```

## 🔧 GitHub Pages Settings

1. Go to your repository on GitHub
2. Click on **Settings**
3. Scroll down to **Pages** section
4. Under **Source**, select **Deploy from a branch**
5. Select **gh-pages** branch
6. Select **/ (root)** folder
7. Click **Save**

## 🚨 Troubleshooting

### Common Issues:

1. **404 Error**: Make sure GitHub Pages is enabled in repository settings
2. **Build Fails**: Check for any syntax errors in the code
3. **Deploy Fails**: Ensure you have push access to the repository

### Solutions:

```bash
# Clear build cache
rm -rf build
npm run build

# Reinstall dependencies
rm -rf node_modules
npm install

# Force deploy
npm run deploy -- --force
```

## 🔄 Automatic Deployment

To set up automatic deployment:

1. **GitHub Actions**: Create `.github/workflows/deploy.yml`
2. **Webhook**: Set up webhook for automatic deployment on push
3. **CI/CD**: Use GitHub Actions for continuous deployment

## 📊 Deployment Status

Check deployment status:
- **GitHub Pages**: Repository Settings → Pages
- **Actions**: Repository Actions tab
- **Build Logs**: Check for any errors

## 🎯 Best Practices

1. **Test Locally**: Always test with `npm start` before deploying
2. **Build Verification**: Check build output before deployment
3. **Version Control**: Commit changes before deploying
4. **Backup**: Keep local backup of working version

## 📞 Support

If you encounter issues:
1. Check GitHub Pages documentation
2. Verify repository permissions
3. Check build logs for errors
4. Ensure all dependencies are installed

## 🎉 Success!

Once deployed successfully, your website will be live at:
**https://tuhinx.github.io/httply**

The website includes:
- ✅ Responsive design for all devices
- ✅ Dark/Light theme toggle
- ✅ Mobile navigation
- ✅ Code examples with copy functionality
- ✅ Complete documentation
- ✅ Professional GitHub-style UI
