import CandidateRow from "./CandidateRow";

const CandidateUserTables = ({users, startIndex}) => {

    return (
        <table className="table mt-5 table-striped">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">Primary Email</th>
                    <th scope="col">Birth Date</th>
                    <th scope="col">GitHub</th>
                    <th scope="col">LinkedIn</th>
                    <th scope="col">View</th>
                    <th scope="col">Delete</th>
                </tr>
            </thead>
            <tbody>
                {users.map((user, index) => {
                    return <CandidateRow key={user.id} index={startIndex+index+1} user={user} />
                })}
            </tbody>
        </table>
    )
}

export default CandidateUserTables;