'use client';

import { User } from '@/types/user';
import { useRouter } from 'next/navigation';
import { Button } from "@/components/ui/button";
import {
    Table, TableBody, TableCell, TableHead, TableHeader, TableRow
} from "@/components/ui/table";

interface Props {
    users: User[];
    onDelete?: (id: string) => void;
}

export default function UserTable({ users }: Props) {
    const router = useRouter();

    return (
        <div className="space-y-4">
            <div className="flex justify-between items-center">
                <h2 className="text-xl font-semibold">Users</h2>
                <Button 
                // onClick={() => router.push('/dashboard/users/create')}
                >
                    Create User
                </Button>
            </div>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>ID</TableHead>
                        <TableHead>Username</TableHead>
                        <TableHead>Email</TableHead>
                        <TableHead>Phone Number</TableHead>
                        <TableHead>Address</TableHead>
                        <TableHead>Actions</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {users.map(user => (
                        <TableRow key={user.id}>
                            <TableCell>{user.id}</TableCell>
                            <TableCell>{user.username}</TableCell>
                            <TableCell>{user.email}</TableCell>
                            <TableCell>{user.phoneNumber}</TableCell>
                            <TableCell>{user.address || 'N/A'}</TableCell>
                            <TableCell>
                                <div className="flex gap-2">
                                    <Button
                                        variant="secondary"
                                    // onClick={() => router.push(`/dashboard/users/${user.id}`)}
                                    >
                                        Edit
                                    </Button>
                                    <Button
                                        variant="destructive"
                                    // onClick={() => onDelete?.(user.id)}
                                    >
                                        Delete
                                    </Button>
                                </div>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </div>
    );
}
