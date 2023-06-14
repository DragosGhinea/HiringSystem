import jwtInterceptor from "../../components/shared/JwtInterceptor";
import { useState, useEffect } from "react";
import JobRow from "./JobRow";
import '../css/managerJobsPage.css'
import { useLocation, useNavigate } from "react-router-dom";

const ManageJobsPage = () => {
    const pageSize = 2;
    const navigate = useNavigate();
    const [jobs,setJobs] = useState();
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);

    const [page, setPage] = useState(parseInt(searchParams.get('page')) || 1);

    useEffect(() => {
        console.log(page)
        jwtInterceptor.get(`http://localhost:8081/api/v1/job/get/all/paginated?page=${page}&size=${pageSize}`)
            .then(data => {
                setJobs(data.data);
            })
    }, [page]);

    const prevPage = () => {
        setPage(prevPage => {
            if(prevPage === 1)
                return prevPage;
            return prevPage - 1;
        });
    }

    const nextPage = () => {
        setPage(prevPage => {
            if(jobs.length===0){
                return prevPage;
            }
            return prevPage + 1;
        });
    }

    if(!jobs)
        return "Loading jobs..."

    return (
        <div className="row">
            <div className="col-10 offset-1 mt-5">
                <div className="manage-jobs">
                    <h1>Manage jobs</h1>
                    <div class="input-group mb-3">
                        <div className="btn btn-primary" onClick={prevPage}>Previous</div>
                        <div className="btn btn-primary" onClick={nextPage}>Next</div>
                        <div className="page">Page {page}</div>
                        <div className="btn btn-primary" onClick={() => {navigate("/jobs/create")}}>Create Job</div>
                    </div>
                    <table className="table mt-5 table-striped">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Title</th>
                                <th scope="col">Type</th>
                                <th scope="col">Position</th>
                                <th scope="col">Salary</th>
                                <th scope="col">Hours/Week</th>
                                <th scope="col">View</th>
                                <th scope="col">Edit</th>
                                <th scope="col">Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            {jobs.map((job, index) => {
                                return <JobRow key={job.id} index={(page-1)*pageSize+index+1} job={job} />
                            })}
                        </tbody>
                    </table>
                    {jobs.length===0 &&
                            <h5 className='mt-5'>No jobs on this page!</h5>
                    }
                </div>
            </div>
        </div>
    );
}

export default ManageJobsPage;