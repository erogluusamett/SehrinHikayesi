import React from 'react';
import { Outlet } from 'react-router-dom';
import UserSidebar from '../components/UserSidebar';
import './SidebarLayout.css';
import { useUser } from '../context/UserContext'; // 👈 user çekiyoruz

const SidebarLayout = () => {
    const { user } = useUser(); // 👈 kullanıcı bilgisi

    return (
        <div className="user-layout">
            <UserSidebar user={user} /> {/* 👈 prop olarak gönderiyoruz */}
            <div className="user-main-content">
                <Outlet />
            </div>
        </div>
    );
};

export default SidebarLayout;
