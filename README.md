# babyTracker

# Team Members
- Jerome Joof
- Romell Pineda
- Sergey Voytov
- Martin Papa
- Dayne Daylong

# Project Description
- This app provides valuable information for users on the developmental milestones of babies in the first year after birth **(0-12) months**. We want to make it easier for the users by providing data based on statistics/research on where their baby should be. The app also has other features like nutrition which notifies users when to feed their babies or the last time their baby was fed, and also investing strategies for the users to better prepare for their baby’s future.

# Team Agreement

* Communication Plan
    * When you are going to be late, let the team know.
        * Slack
        * Phone numbers
        * Email
        * Text
* Conflict Plan
    * Majority vote (There are 5 people in the group so no need for a tie breaker)
    * We will try to resolve issue internally before we escalate.
    * If escalation is really needed, we will talk as a team.
* Work Plan
    * GitHub Project
    * Stand-Up in the morning and by end of day.
    * No work on the weekends.
    * Weekdays - 9 am to 5 pm.
* Git process
    * Development branch will be created.
    * Master is protected. 
        * 1 approver
* Create branch for every feature that we will implement.

# Project Proposals

**Idea 1: Baby Tracker**

*Summary:* 

This app provides valuable information for users on the developmental milestones of babies in the first year after birth (0-12) months. We want to make it easier for the users by providing data based on statistics/research on where their baby should be. The app also has other features like nutrition which notifies users when to feed their babies or the last time their baby was fed, and also investing strategies for the users to better prepare for their baby’s future.

*Pain Point App Addresses:* 
This application is being created to assist new parents with tracking the development of their new child.  Baby Tracker provides an easily accessible resource for newborn development information including nutrition, and investing.  Stretch goals would implement an IOT button that when pressed would trigger a lambda function that order diapers.

*Minimum MVP Definition:*
Users will be able to securely log in and save/track data pertinent to their child’s development and to see if their child is on track with most children their age. 
AWS Implementations:
- IAM
- Cognito
- Notifications
- DynamoDB
- Amazon Neptune (if new AWS system required)
- AWS Device Farm
   * is an application testing service that lets you improve the quality of your web and mobile apps by testing them across an extensive range of desktop browsers and real mobile devices
   * The service enables you to run your tests concurrently on multiple desktop browsers or real devices to speed up the execution of your test suite, and generates videos and logs to help you quickly identify issues with your app.
- Stretch Goal: S3
- Stretch Goal: Lambda (w/IOT button)

**Idea 2: TAQ**

*Summary:* 

This app will enable Code Fellows students to enter their names in the queue when they need help from a TA with their labs. The TAQ app will be used by both TAs and students to confirm who is next in line for assistance.

*Pain Point App Addresses:*
Addresses multiple students waiting for teaching aide availability without knowledge of other students who are waiting ahead of them.

*Minimum MVP Definition:*
A queue with a student sign-up.  App will alert the first student waiting for assistance when the first TA becomes available.


- User Story 1
   * As a new parent I would like to know the development milestones for my new born
- User Story 2
   * As a new Dad I want to be able to save shopping list and check off when I do shopping
- User Story 3.
   * As a user I want to have nutritional information for my baby. Implement notification for parents who feed their baby by specific hour/time.
- User Story 4.
   * As a user I want to have the ability to create a gallery photos of my child
- User Story 5
   * As a user I want to add multiple children to my profile
- User Story 6
   * As a user I want to have the ability to save data into my profile regarding my baby's developmental milestone

## Wireframe
<object data="https://github.com/jjblues86/babyTracker/blob/jj/assets/baby_tracker_v1.pdf" type="application/pdf" width="800px" height="auto">
    <embed src="https://github.com/jjblues86/babyTracker/blob/jj/assets/baby_tracker_v1.pdf">
        <p>This browser does not support PDFs. Please download the PDF to view it: <a href="https://github.com/jjblues86/babyTracker/blob/jj/assets/baby_tracker_v1.pdf">Download PDF</a>.</p>
    </embed>
</object>

# Database Entity-Relationship-Diagram
![ERD](assets/babyTrackerERD.png)



