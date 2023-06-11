import "./App.css";
import Layout from "./components/shared/Layout";
import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import { AuthContextProvider } from "./components/shared/AuthContext";
import { JobContextProvider } from "./components/shared/JobContext";
import ProtectedRoute from "./components/shared/ProtectedRoute";
import MainPage from "./pages/MainPage";
import Register from "./pages/Register";
import CandidateProfile from "./pages/users/CandidateProfile";
import InterviewerProfile from "./pages/users/InterviewerProfile";
import ManagerProfile from "./pages/users/ManagerProfile";
import CreateJob from "./pages/jobs/CreateJob";
import EditJob from "./pages/jobs/EditJob";
import CreateInterviewConferenceRoom from "./pages/interviewCrud/CreateInterviewConferenceRoom";
import DisplayInterviewConferenceRoom from "./pages/interviewCrud/DisplayInterviewConferenceRoom";
import EditInterviewConferenceRoom from "./pages/interviewCrud/EditInterviewConferenceRoom";
import InterviewRoomPage from "./pages/interviewRoom/InterviewRoomPage";
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
                  path="/job/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/edit/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/delete/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <DeleteJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/get/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/view/job/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ViewJobPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/display/jobs"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <DisplayJobsPage />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/all-applications/:jobId"
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
                  path="/interview/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/display/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <DisplayInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/edit/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <EditInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/delete/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
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
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateManualCandidate />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviewer/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateManualInterviewer />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/manager/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
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
          </Routes>
        </Layout>
      </JobApplicationContextProvider></JobContextProvider></AuthContextProvider>
    </>
  );
}

export default App;
