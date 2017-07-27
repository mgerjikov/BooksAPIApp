# BooksAPIApp

<p><b> Book Listing App - project from Android Basics by Google Nanodegree Program in Udacity </b></p>


The goal is to design and create the structure of a Book Listing app which would allow a user to get a list of published books on a given topic. You will be using the google books api in order to fetch results and display them to the user.

This project is about combining various ideas and skills we’ve been practicing throughout the course. They include:

 - Fetching data from an API
 - Using an AsyncTask
 - Parsing a JSON response
 - Creating a list based on that data and displaying it to the user.
 
![screenshot_1501167420](https://user-images.githubusercontent.com/17390877/28678486-3a0419da-72f9-11e7-989e-e39f0b822b77.png)
![screenshot_1501167446](https://user-images.githubusercontent.com/17390877/28678487-3a06fe7a-72f9-11e7-9a64-dcbbadfd6f49.png)
![screenshot_1501167516](https://user-images.githubusercontent.com/17390877/28678489-3a0e88e8-72f9-11e7-8744-28e671396abe.png)
![screenshot_1501167593](https://user-images.githubusercontent.com/17390877/28678488-3a0dca5c-72f9-11e7-9675-587313b948a1.png)
![screenshot_1501167614](https://user-images.githubusercontent.com/17390877/28678490-3a17d150-72f9-11e7-8ae8-1c666f382cfa.png)
 
 #  PROJECT SPECIFICATION Book Listing
 
 <p><b> CRITERIA / MEETS SPECIFICATIONS </b></p>
 
 <p><b>Layout</b></p>
 
<p><b>Overall Layout </b>/ App contains a ListView which becomes populated with list items.</p>

<p><b>List Item Layout</b> / List Items display at least author and title information.</p>

<p><b>Layout Best Practices</b> / The code adheres to all of the following best practices:</p>

 - Text sizes are defined in sp
 - Lengths are defined in dp
 - Padding and margin is used appropriately, such that the views are not crammed up against each other.
<p><b>Text Wrapping</b> / Information displayed on list items is not crowded.</p>

<p><b>Rotation</b> / Upon device rotation -</p>

 - The layout remains scrollable.
 - The app should save state and restore the list back to the previously scrolled position.
 - The UI should adjust properly so that all contents of each list item is still visible and not truncated.
 - The Search button should still remain visible on the screen after the device is rotated.
 
<p><b>Functionality</b></p>
  
<p><b>Runtime Errors</b> / The code runs without errors.</p>

<p><b>API Call</b> / The user can enter a word or phrase to serve as a search query. The app fetches book data related to the query via an HTTP request from the Google Books API, using a class such as HttpUriRequest or HttpURLConnection.</p>
<p><b>Response Validation</b> / The app checks whether the device is connected to the internet and responds appropriately. The result of the request is validated to account for a bad server response or lack of server response.</p>

<p><b>Async Task</b> / The network call occurs off the UI thread using an AsyncTask or similar threading object.</p>

<p><b>JSON Parsing</b> / The JSON response is parsed correctly, and relevant information is stored in the app.</p>

<p><b>ListView Population </b>/ The ListView is properly populated with the information parsed from the JSON response.</p>

<p><b>No Data Message</b> / When there is no data to display, the app shows a default TextView that informs the user how to populate the list.</p>

<p><b>External Libraries and Packages</b> / The intent of this project is to give you practice writing raw Java code using the necessary classes provided by the Android framework; therefore, the use of external libraries for core functionality will not be permitted to complete this project.</p>
  
<p><b>Code Readability</b></p>  
  
<p><b>Naming Conventions</b> / All variables, methods, and resource IDs are descriptively named such that another developer reading the code can easily understand their function.</p> 

<p><b>Format</b> / The code is properly formatted i.e. there are no unnecessary blank lines; there are no unused variables or methods; there is no commented out code. The code also has proper indentation when defining variables and methods.</p> 
  
  
  
  
  
  
  
  
  
  
  
  
