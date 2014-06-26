# Simple Twitter Client - fragments

This is a simple Twitter client with fragments. The home screen shows the user's home timeline, in reverse chronological order. Inifinite scrolling is supported so it's easy to see older tweets. Links and images within each tweet are clickable so users can see more details. Composing tweets is supported with character count. In profile screen you can see details about the user.

Time spent: 10 hours spent in total

Completed user stories:

 * [x] Required: User can sign in to Twitter using OAuth login
 * [x] Required: User can view the tweets from their home timeline
       		      User should be able to see the username, name, body and timestamp for each tweet
		      User should be displayed the relative timestamp for a tweet "8m", "7h"
		      User can view more tweets as they scroll with infinite pagination
		      [x] Optional: Links in tweets are clickable and will launch the web browser (see autolink)
 * [x] Required: User can compose a new tweet
       		      User can click a “Compose” icon in the Action Bar on the top right
		      User can then enter a new tweet and post this to twitter
		      User is taken back to home timeline with new tweet visible in timeline
 		      [x] Optional: User can see a counter with total number of characters left for tweet
 * [x] Required: User can switch between Timeline and Mention views using tabs
       		      User can view their home timeline tweets
		      User can view the recent mentions of their username
		      User can scroll to bottom of either of these lists and new tweets will load ("infinite scroll")
 * [x] Required: User can navigate to view their own profile
       		      User can see picture, tagline, # of followers, # of following, and tweets on their profile
 * [x] Required: User can click on the profile image in any tweet to see another user's profile
       		      User can see picture, tagline, # of followers, # of following, and tweets of clicked user
		      Profile view should include that user's timeline
 * [x] Advanced: When a network request is sent, user sees an indeterminate progress indicator
 * [x] Advanced: Improve the user interface and theme the app to feel twitter branded
 
Notes:

* I didn't implement tweet detail view, but this app does allow users to examine images with a new activity. Different res images are downloaded for thumbnails and image display activity
* Tweet compose view also loads user's profile image, name and screenname. Character count color would change if the count is over limit
* Images have inset shadows

Walkthrough of all user stories:

![Video Walkthrough](anim_twitter_client_2.gif)
