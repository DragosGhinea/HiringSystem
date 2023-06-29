import "./App.css";
import Layout from "./components/shared/Layout";
import { Route, Routes, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import { AuthContextProvider } from "./components/shared/AuthContext";
import { JobContextProvider } from "./components/shared/JobContext";
import ProtectedRoute from "./components/shared/ProtectedRoute";
import MainPage from "./pages/MainPage";
import Register from "./pages/register/Register";
import CandidateProfile from "./pages/users/CandidateProfile";
import InterviewerProfile from "./pages/users/InterviewerProfile";
import ManagerProfile from "./pages/users/ManagerProfile";
import CreateJob from "./pages/jobs/CreateJob";
import EditJob from "./pages/jobs/EditJob";
import CreateInterviewConferenceRoom from "./pages/interviewCrud/CreateInterviewConferenceRoom";
import DisplayInterviewConferenceRoom from "./pages/interviewCrud/DisplayInterviewConferenceRoom";
import EditInterviewConferenceRoom from "./pages/interviewCrud/EditInterviewConferenceRoom";
import InterviewLeft from "./pages/interviewRoom/InterviewLeft";
import InterviewWaitingRoom from "./pages/interviewRoom/InterviewWaitingRoom";
import CreateApplication from "./pages/jobApplications/CreateApplication";
import {JobApplicationContextProvider} from "./components/shared/JobApplicationContext";
import DeleteJob from "./pages/jobs/DeleteJob";
import DeleteApplication from "./pages/jobApplications/DeleteApplication";
import CreateManualCandidate from "./pages/users/CreateManualCandidate";
import CreateManualInterviewer from "./pages/users/CreateManualInterviewer";
import CreateManualManager from "./pages/users/CreateManualManager";
import DisplayJobsPage from "./pages/jobs/DisplayJobsPage";
import ViewJobPage from "./pages/jobs/ViewJobPage";
import AllJobApplicationsPage from "./pages/jobApplications/AllJobApplicationsPage";
import MyApplicationsPage from "./pages/myApplications/MyApplicationsPage";
import MyInterviewsPage from "./pages/myInterviews/MyInterviewsPage";
import ManageJobsPage from "./pages/manageJobs/ManageJobsPage";
import ManageUsersPage from "./pages/manageUsers/ManageUsersPage";
import ManageInterviewsPage from "./pages/manageInterviews/ManageInterviewsPage";
import ProfilePage from "./pages/users/ProfilePage";
import TermsOfUsePage from "./pages/TermsOfUsePage";
import RegisterSent from "./pages/register/RegisterSent";
import RegisterConfirm from './pages/register/RegisterConfirm'

function App() {
  return (
    <>
      <AuthContextProvider><JobContextProvider><JobApplicationContextProvider>
        <Layout>
          <Routes>
            <Route path="/" element={<MainPage />}></Route>
 
            <Route
              path="/login"
              element={
                <ProtectedRoute accessBy="non-authenticated">
                  <Login />
                </ProtectedRoute>
              }
            ></Route>
            <Route
              path="/register"
              element={
                <ProtectedRoute accessBy="non-authenticated">
                  <Register />
                </ProtectedRoute>
              }
            ></Route>
            <Route
                exact
                path="/profile"
                element={<Navigate to="/profile/me" />}
            />
            <Route
                  path="/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ProfilePage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/candidate/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CandidateProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviewer/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <InterviewerProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/manager/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ManagerProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/jobs/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/jobs/edit/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/jobs/delete/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <DeleteJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/jobs/get/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/jobs/job/:id"
                  element={
                    <ViewJobPage />
                  }
              ></Route>
              <Route
                  path="/jobs"
                  element={
                    <DisplayJobsPage />
                  }
              ></Route>

            <Route
                  path="/jobs/manage"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ManageJobsPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/jobs/applications/:jobId"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <AllJobApplicationsPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/application/create/:jobId"
                  element={<ProtectedRoute accessBy="authenticated">
                      <CreateApplication />
                  </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/application/delete/:jobApplicationId"
                  element={<ProtectedRoute accessBy="authenticated">
                      <DeleteApplication />
                  </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviews/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviews/display/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <DisplayInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviews/edit/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviews/delete/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/room/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <InterviewWaitingRoom />
                      </ProtectedRoute>
                  }
              ></Route>

                <Route
                  path="/interview/room/left"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <InterviewLeft />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/candidate/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateManualCandidate />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviewer/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateManualInterviewer />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/manager/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateManualManager />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/my-applications"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <MyApplicationsPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/my-interviews"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <MyInterviewsPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/users/manage"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ManageUsersPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviews/manage"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ManageInterviewsPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/terms-of-use"
                  element={
                        <TermsOfUsePage />
                  }
              ></Route>
               <Route
                  path="/register/sent"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <RegisterSent />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/register/confirm/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <RegisterConfirm />
                      </ProtectedRoute>
                  }
              ></Route>
          </Routes>
        </Layout>
      </JobApplicationContextProvider></JobContextProvider></AuthContextProvider>
    </>
  );
}

export default App;
