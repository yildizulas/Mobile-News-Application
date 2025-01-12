# **Mobile News App**

**Mobile News App** is an Android application that allows users to view, drill down, and comment on news by category. The app communicates with a RESTful News API to retrieve data and provide users with an interactive experience.

---

## **Features**

### **1. Main Activity**
- Displays news categories in a tab layout.
- Shows a list of news items (image, title, and short description) based on the selected category.
- Enables navigation between categories by swiping left and right.
- Shows a progress indicator (spinner) while loading data.

### **2. News Details Activity**
- Displays details of the selected news item, including:
  - Image
  - Headline
  - Date
  - Full text
- Content is displayed on a scrollable screen.
- Navigation:
  - Back button to return to the main page.
  - Comments menu to navigate to the comments screen.

### **3. Comments Activity**
- Lists all user comments for a news item using a RecyclerView.
- Each comment includes:
  - The name of the commenter.
  - The message content.
- Displays a progress bar while loading comments.
- Provides a button to navigate to the "Add Comment" screen.

### **4. Add Comment Activity**
- Allows users to:
  - Enter their name and message.
  - Post a comment.
- Validates input fields:
  - Notifies the user if the name or message is missing and prevents submission.
- Updates the comments list on successful submission.
- Displays a progress bar during data submission.

---

## **Usage**

### **1. Home Page (Category and News)**
- When the app launches, categories and related news are displayed in a tab layout.
- Swipe left or right to switch between categories.

### **2. News Details**
- Select a news item from the home page to view its detailed content.

### **3. Comments**
- Press the "View Comments" button on the news details screen to see user comments.

### **4. Adding Comments**
- Use the "Add Comment" button on the comments screen to leave a comment on a news item.
- Ensure the name and message fields are filled in before submitting.

---

## **Technologies**

### **1. Android Development**
- **UI Components**: RecyclerView, TabLayout, ViewPager2, EditText, Button, ScrollView

### **2. RESTful API Integration**
- HTTP requests to fetch and post data.
- JSON parsing for processing API responses.

### **3. User Experience**
- Loading indicators to show progress during data operations.
- Smooth scrolling and transition effects for better usability.

---

This README outlines the main features, usage instructions, and technologies used in the **Mobile News App** project. Feel free to customize it further based on your project's specific requirements.
