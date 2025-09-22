import UserTable from "@/components/admin/users/UserTable";
import { User } from "@/types/user";

export default async function UsersPage() {
    // Sau này thay URL backend thật của bạn
    const users: User[] = [
        { id: "1", username: "john_doe", email: "johndoe@gmail.com", phoneNumber: "0123456789", address: "123 Main St, Cityville" },
        { id: "2", username: "john_doe", email: "johndoe@gmail.com", phoneNumber: "0123456789", address: "123 Main St, Cityville" },
        { id: "3", username: "john_doe", email: "johndoe@gmail.com", phoneNumber: "0123456789", address: "123 Main St, Cityville" },
        { id: "4", username: "john_doe", email: "johndoe@gmail.com", phoneNumber: "0123456789", address: "123 Main St, Cityville" },
        { id: "5", username: "john_doe", email: "johndoe@gmail.com", phoneNumber: "0123456789", address: "123 Main St, Cityville" },
    ];

    const deleteUser = async (id: string) => {
        users.splice(users.findIndex(user => user.id === id), 1);
    }

    // hàm xoá sẽ viết ở client component (UserTable)
    return (
        <div className="p-6">
            <UserTable
                users={users}
            // onDelete={async (id) => { await fetchAPI(`http://localhost:8080/api/users/${id}`, {method:'DELETE'})}}
            />
        </div>
    );
}