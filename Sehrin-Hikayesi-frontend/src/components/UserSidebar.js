import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './UserSidebar.css';
import MemoryModal from './MemoryModal';
import SearchBar from './SearchBar';

const UserSidebar = () => {
    const [showModal, setShowModal] = useState(false);
    const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);
    const [showSearch, setShowSearch] = useState(false);
    const [profileImage, setProfileImage] = useState(null);

    const selectedCity = "Malatya";
    const isLoggedIn = !!localStorage.getItem("token");
    const navigate = useNavigate();

    useEffect(() => {
        const storedImage = localStorage.getItem("profileImage");
        if (storedImage) {
            setProfileImage(`http://localhost:8585/uploads/profile/${storedImage}`);
        }
    }, []);

    const handleLogout = () => {
        localStorage.clear();
        navigate("/login");
    };

    return (
        <>
            <div className="user-sidebar">
                <Link to="/map" className="user-nav-item">
                    <i className="fas fa-home"></i><span>Ana Sayfa</span>
                </Link>

                <div className="user-nav-item" onClick={() => setShowSearch(!showSearch)}>
                    <i className="fas fa-search"></i><span>Ara</span>
                </div>

                <Link to="/explore" className="user-nav-item">
                    <i className="fas fa-compass"></i><span>Keşfet</span>
                </Link>

                {isLoggedIn ? (
                    <>
                        <div className="user-nav-item" onClick={() => setShowModal(true)}>
                            <i className="fas fa-plus-square"></i><span>Oluştur</span>
                        </div>

                        <Link to="/profile" className="user-nav-item">
                            <img
                                src={profileImage || process.env.PUBLIC_URL + '/assets/profile.jpeg'}
                                alt="Profil"
                                className="user-profile-pic"
                            />
                            <span>Profil</span>
                        </Link>

                        <div className="user-nav-item" onClick={() => setShowLogoutConfirm(true)}>
                            <i className="fas fa-sign-out-alt"></i><span>Çıkış</span>
                        </div>
                    </>
                ) : (
                    <>
                        <Link to="/login" className="user-nav-item">
                            <i className="fas fa-sign-in-alt"></i><span>Giriş Yap</span>
                        </Link>
                        <Link to="/register" className="user-nav-item">
                            <i className="fas fa-user-plus"></i><span>Kayıt Ol</span>
                        </Link>
                    </>
                )}
            </div>

            {/* Anı oluşturma modali */}
            {showModal && (
                <MemoryModal
                    selectedCity={selectedCity}
                    onClose={() => setShowModal(false)}
                />
            )}

            {/* Çıkış onayı */}
            {showLogoutConfirm && (
                <div className="logout-confirm-overlay">
                    <div className="logout-confirm-modal">
                        <p>Çıkış yapmak istediğinize emin misiniz?</p>
                        <div className="confirm-buttons">
                            <button className="yes" onClick={handleLogout}>Evet</button>
                            <button className="no" onClick={() => setShowLogoutConfirm(false)}>Hayır</button>
                        </div>
                    </div>
                </div>
            )}

            {/* Arama bileşeni */}
            {showSearch && (
                <SearchBar onClose={() => setShowSearch(false)} />
            )}
        </>
    );
};

export default UserSidebar;
