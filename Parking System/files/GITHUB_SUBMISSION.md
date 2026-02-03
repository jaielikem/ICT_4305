# GitHub Submission Instructions

## Step 1: Create a GitHub Repository

1. Go to https://github.com and sign in
2. Click the "+" icon in the top right and select "New repository"
3. Repository settings:
   - Name: `parking-system-week4` (or your preferred name)
   - Description: "University Parking System - OOP Week 4 Assignment"
   - Visibility: Public or Private (your choice)
   - ✅ Initialize with README (you can replace it)
4. Click "Create repository"

## Step 2: Upload Your Files

### Option A: Using Git Command Line

```bash
# Navigate to your project directory
cd parking-system

# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit the files
git commit -m "Initial commit - Week 4 Parking System Assignment"

# Add your GitHub repository as remote
git remote add origin https://github.com/YOUR_USERNAME/parking-system-week4.git

# Push to GitHub
git push -u origin main
```

### Option B: Using GitHub Web Interface

1. In your new GitHub repository, click "uploading an existing file"
2. Drag and drop all folders and files from the parking-system directory
3. Scroll down and click "Commit changes"

### Option C: Using GitHub Desktop

1. Open GitHub Desktop
2. File → Add Local Repository → Choose the parking-system folder
3. Click "Publish repository" in the top right
4. Select visibility and click "Publish Repository"

## Step 3: Verify Your Upload

Check that your repository contains:

```
✅ src/main/java/com/university/parking/
   - Address.java
   - Car.java
   - CarType.java
   - Customer.java
   - ParkingLot.java
   - ParkingTransaction.java

✅ src/test/java/com/university/parking/
   - AddressTest.java
   - CarTest.java
   - CarTypeTest.java
   - CustomerTest.java
   - ParkingLotTest.java
   - ParkingTransactionTest.java

✅ Root directory files:
   - pom.xml
   - README.md
   - REFLECTION.md
   - CLASS_DIAGRAM.mermaid
   - PROJECT_STRUCTURE.md
   - verify.sh
```

## Step 4: Get Your Repository URL

1. On your GitHub repository page, click the green "Code" button
2. Copy the HTTPS URL (looks like: `https://github.com/YOUR_USERNAME/parking-system-week4.git`)

## Step 5: Submit to Canvas

1. Go to the Canvas assignment page
2. In the submission field, paste your GitHub repository URL
3. **Also upload the following files directly to Canvas**:
   - REFLECTION.md
   - README.md
   - All .java files from src/main/java
   - All test files from src/test/java
4. Click "Submit Assignment"

## Additional Tips

### Making Your README Display Nicely on GitHub

Your README.md file will automatically display on your repository's main page. GitHub will render:
- Headers
- Code blocks
- Lists
- Links

### Viewing the Class Diagram

To view the CLASS_DIAGRAM.mermaid file:
1. **On GitHub**: Click the file, GitHub will render it automatically
2. **Locally**: Use Mermaid Live Editor (https://mermaid.live/)
   - Copy the contents of CLASS_DIAGRAM.mermaid
   - Paste into Mermaid Live Editor
   - View the rendered diagram

### If You Need to Make Changes

```bash
# Make your changes to the files
# Then commit and push again:

git add .
git commit -m "Description of changes"
git push
```

## Submission Checklist

Before submitting, verify:

- [ ] All Java source files are present
- [ ] All test files are present
- [ ] README.md is complete
- [ ] REFLECTION.md is 250-500 words (current: 487 words)
- [ ] CLASS_DIAGRAM.mermaid is present
- [ ] pom.xml is present for Maven builds
- [ ] Code compiles without errors
- [ ] Repository URL is correct
- [ ] Files uploaded to Canvas
- [ ] Reflection includes Turabian citations

## Common Issues and Solutions

**Issue**: Git says "repository already exists"
**Solution**: Use `git pull` first, then `git push`

**Issue**: Can't push to GitHub (authentication error)
**Solution**: Use a Personal Access Token instead of password
- Go to GitHub Settings → Developer Settings → Personal Access Tokens
- Generate new token with "repo" permissions
- Use token as password when pushing

**Issue**: Files too large for GitHub
**Solution**: This project should be well under GitHub's limits. If you added large files, consider using .gitignore

## Need Help?

- GitHub Documentation: https://docs.github.com
- Git Basics: https://git-scm.com/doc
- Canvas Support: Contact your instructor or TA

Good luck with your submission!
