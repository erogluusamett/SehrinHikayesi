import React, { useEffect, useState } from 'react';
import axios from 'axios';
import EditProfileModal from './EditProfileModal';
import './ProfilePage.css';

const ProfilePage = () => {
    const [user, setUser] = useState(null);
    const [modalOpen, setModalOpen] = useState(false);
    const [memories, setMemories] = useState([]);
    const [activeMenu, setActiveMenu] = useState(null); // 3 nokta menüsü için
    const [confirmDeleteId, setConfirmDeleteId] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");

        axios.get("http://localhost:8585/api/users/me", {
            headers: { Authorization: `Bearer ${token}` }
        }).then(res => setUser(res.data));

        axios.get("http://localhost:8585/api/stories/user", {
            headers: { Authorization: `Bearer ${token}` }
        }).then(res => {
            const sorted = res.data.reverse(); // ✅ en yeni anı en üste gelsin
            setMemories(sorted);
        });
    }, []);

    const handleDelete = async (id) => {
        const token = localStorage.getItem("token");
        try {
            await axios.delete(`http://localhost:8585/api/stories/${id}`, {
                headers: { Authorization: `Bearer ${token}` }
            });

            setMemories(prev => prev.filter(memory => memory.id !== id));
            setConfirmDeleteId(null);
        } catch (err) {
            console.error("Silme hatası:", err);
        }
    };

    if (!user) return <p>Yükleniyor...</p>;

    return (
        <div className="profile-container">
            <div className="profile-header">
                <img
                    src={
                        user.profileImage
                            ? `http://localhost:8585/uploads/profile/${user.profileImage}`
                            : "/default-avatar.png"
                    }
                    className="profile-img"
                    alt="Profil"
                />
                <div className="profile-info">
                    <h2>@{user.username}</h2>
                    <p>{user.name}</p>
                    <button onClick={() => setModalOpen(true)}>Düzenle</button>
                </div>
            </div>

            <div className="profile-memories">
                <h3>Anılarım</h3>
                <div className="memory-list">
                    {memories.map((memory) => (
                        <div className="memory-card" key={memory.id}>
                            <div className="memory-options">
                                <span onClick={() => setActiveMenu(activeMenu === memory.id ? null : memory.id)}>
                                    &#8942;
                                </span>
                                {activeMenu === memory.id && (
                                    <div className="options-dropdown">
                                        <button onClick={() => setConfirmDeleteId(memory.id)}>Sil</button>
                                    </div>
                                )}
                            </div>
                            <img src={memory.imageUrl} alt={memory.title} />
                            <h4>{memory.title}</h4>
                            <p>{memory.content}</p>
                        </div>
                    ))}
                </div>
            </div>

            {confirmDeleteId && (
                <div className="delete-confirm-overlay">
                    <div className="delete-confirm-box">
                        <p>Bu anıyı silmek istiyor musun?</p>
                        <button className="yes" onClick={() => handleDelete(confirmDeleteId)}>Evet</button>
                        <button className="no" onClick={() => setConfirmDeleteId(null)}>Hayır</button>
                    </div>
                </div>
            )}

            {modalOpen && (
                <EditProfileModal
                    user={user}
                    onClose={() => setModalOpen(false)}
                    onUpdate={setUser}
                />
            )}
        </div>
    );
};

export default ProfilePage;
