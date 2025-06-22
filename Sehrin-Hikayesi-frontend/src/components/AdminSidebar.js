import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './AdminSidebar.css';
import LogoutModal from './LogoutModal';

const AdminSidebar = () => {
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);

    const handleLogout = () => {
        localStorage.clear();
        navigate('/');
    };

    return (
        <div className="admin-sidebar">
            <Link to="/admin/users" className="admin-nav-item">
                <i className="fas fa-users"></i> Kullanıcı Yönetimi
            </Link>
            <Link to="/admin/stories" className="admin-nav-item">
                <i className="fas fa-book"></i> Anı Yönetimi
            </Link>
            <Link to="/admin/categories" className="admin-nav-item">
                <i className="fas fa-layer-group"></i> Kategori Yönetimi
            </Link>
            <Link to="/admin/tags" className="admin-nav-item">
                <i className="fas fa-tag"></i> Etiket Yönetimi
            </Link>
            <Link to="/admin/stats" className="admin-nav-item">
                <i className="fas fa-chart-line"></i> İstatistikler
            </Link>

            <div className="admin-nav-item logout-link" onClick={() => setShowModal(true)}>
                <i className="fas fa-sign-out-alt"></i> Çıkış Yap
            </div>

            {showModal && (
                <LogoutModal
                    onConfirm={handleLogout}
                    onCancel={() => setShowModal(false)}
                />
            )}
        </div>
    );
};

export default AdminSidebar;
