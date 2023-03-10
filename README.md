# Hiring System Web Application

This project represents a web application made specifically for one company to help with their hiring procedures. It is designed to help with user & application management and to offer interview scheduling and incorporated conference rooms. It also helps with emails and applicants' notifications. A full list of features shall be provided as the project advances.

## Team

The team that works on this project is composed of:
  - Ghinea Dragoș-Dumitru
  - Iliescu Gabriel-Bogdan
  - Rîncu Ștefania
  - Trufaș Dafina

## User Stories
(20 user stories)

- As an applicant, I want to be able to understand the login form easily.
- As an applicant, I want to be able to modify my personal data (maybe I have inserted something wrong).
- As an applicant, I want the platform to propagate the modifications made to my profile, to all my sent applications.
- As an applicant, I want to receive, per email/notification, details regarding the interviews I was accepted for, such as their general type (technical / HR).
- As an applicant, before the interview day, I want to receive an e-mail/message with the date and time set for the next interview so I will not forget about it.
- As an applicant, I want to be able to request interview date changes.
- As an applicant, if my application is accepted I want to have a calendar from which to select from the available dates the date that fits best for my next interview.
- As an applicant, I want to be notified of any interview changes.
- As an applicant, I want the interview conference room to be easy to access and use.
- As an applicant, I want to be able to withdraw my applications.
- As an applicant, I want to be able to check the status of my applications, at any time.
- As an applicant, I would like the application process to be transparent. I want to be informed, from the very beginning, about all its stages.
- As an applicant, I would like to be able to filter the offered positions, on criteria such as: requirements, period, location.
- As an interviewer, I want my applicants to have valid accounts and not impersonate other people.
- As an interviewer, I want to be able to filter the applications based on keywords and specific profile requirements.
- As an interviewer, I need to have the applicants listed on different criteria and to have a table with their contact dates so I can easily communicate with them.
- As an interviewer, I want the applicants to be properly informed about the interviews, so they are able to prepare and reach their maximum potential during the assessment.
- As both an interviewer and an applicant, I want a clean and secure interviewing process, without being interrupted.
- As both an interviewer and an applicant, I wish to see specific messages for any incorrect operations performed, so I would know what to change (the type of the data inserted is not correct, the field is required, the conference room is busy).
- As both an interviewer and an applicant, I don’t want the platform to display announcements for jobs that aren’t available anymore.

## Backlog

### » Database Actions
- Create a database connection system.
- Create a utility class for user management.
- Create a utility class for jobs and job-applications management [it should include methods for filtering positions or applications on different criteria (both for candidate’s interface and for recruiter’s one].
- Create a utility class for interviews.


### » User Management Basic

- Design user types (Anonymous, Candidate, Interviewer, Manager, etc).
- Design properties and behaviors for defined users (personal data, actions to change this data).
- Create database objects for the designed users.
        - Create application-level classes for those users.


### » User Management Advanced
- Create a user registration system.
- Create a manual user creation system.
- Create a user authentication system.
- Create a profile page to display data.
- Implement file-upload.
- Modify this page to permit data actualization.
- Create a ‘my applications’ page.
- Create a ‘my interviews’ page.


### » User Emailing and Notifications
- Create an email sending system.
- Account validation via email
- Password change via email
- Create different email templates.
- (Email change via email)
- Notification system (via email, but also a notifications center)
- (Message sending between users)?


### » Job-Applications Basic
- Design the job and job-application entities, including properties.
- Create database objects for the above designed entities.
- Create application-level classes for those entities.
- Create an application status system to ensure knowing the point of each application at all times.


### » Job Applications Advanced
- Create pages for displaying jobs and per each job page.
- Create a system for job management (create, delete, edit).
- Create a system for per job applications management (application create, delete) [candidates can delete an application by deciding to withdraw it before it is reviewed].
- Create the submit application page, based on the job settings.
- Create a page for viewing all the applications (for a job).


### » Interview Conference Room
- Design the conference room’s formats (communication types, types and number of users allowed in one room, enter data).
- Create database objects for the conference room.
- Create application-level classes for the conference room.
- Create a user session system for the room.
- Implement a chat system in the room.
- Implement audio & video sharing for each user in the room.
- Implement a separate waiting room for the conference.
- Implement a close room system.



### » Security
- Establish user permissions (what can each user type access, do in the application).
- Modify the application to apply the established permissions.
- Create a terms of use and privacy policy.
- Create a secured system for interview room joining.


### » Design/Frontend
- Definitize design for user related pages.
- Definitize design for job related pages.
- Definitize design for application related pages.
- Definitize design for interview related pages.


### » User Experience Helpers
- Create the user-journey UML diagram.
- Create a FAQ page.
- Create a welcome-page, which includes the description of the application process (all the stages).
- Implement validations, for a better USX.


### » Testing
- automatic tests for the essential features.
