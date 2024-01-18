import { useEffect, useState } from "react";
import AddFriend from "./AddFriend";

const AddFriendModal = ({ close, userID, users }) => {
    const [userFilter, setUserFilter] = useState("");
    const [filteredUsers, seTFilteredUsers] = useState(users);

    const filterUsers = (text) => {
        return [...users].filter(element => element.username.includes(text));
    }

    useEffect(() => {
        seTFilteredUsers(filterUsers(userFilter));
    }, [userFilter, users])

    return (
        <div id="modal-background">
            <div id="modal">
                <div id="heading">
                    <button onClick={close}>
                        <svg id="close-button" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" className="bi bi-x-lg" viewBox="0 0 16 16">
                            <path stroke="black" strokeWidth="0.3" d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8z"/>
                        </svg>
                    </button>
                    <h1>Dodaj znajomego</h1>
                </div>
                <div id="input">
                   <input value={userFilter} onChange={e => setUserFilter(e.target.value)} type="text" name="username" id="username" placeholder="Filtruj"></input>
                </div>
                <div id="data">
                    {filteredUsers.map((x, i) => {
                        return <AddFriend user={x} userID={userID}></AddFriend>
                    })}
                 </div>
             </div>
        </div>  
    )
};

export default AddFriendModal;