import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './ProfilePublicPage.css';

const ProfilePublicPage = () => {
    const { username } = useParams();
    const [user, setUser] = useState(null);
    const [memories, setMemories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProfileData = async () => {
            try {
                const token = localStorage.getItem("token");
                const headers = token ? { Authorization: `Bearer ${token}` } : {};

                const userRes = await axios.get(`http://localhost:8585/api/users/username/${username}`, { headers });
                setUser(userRes.data);

                const memoryRes = await axios.get(`http://localhost:8585/api/stories/by-username/${username}`, { headers });
                const sorted = memoryRes.data.reverse();
                setMemories(sorted);
            } catch (err) {
                console.error("Profil verileri alınamadı:", err);
                setError("Kullanıcı bulunamadı veya veri alınamadı.");
            } finally {
                setLoading(false);
            }
        };

        fetchProfileData();
    }, [username]);

    if (loading) return <p>Yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>{error}</p>;
    if (!user) return <p>Kullanıcı bulunamadı.</p>;

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
                </div>
            </div>

            <div className="profile-memories">
                <h3>{user.username} kullanıcısının anıları</h3>
                <div className="memory-list">
                    {memories.length > 0 ? (
                        memories.map((memory) => (
                            <div className="memory-card" key={memory.id}>
                                <img src={memory.imageUrl} alt={memory.title} />
                                <h4>{memory.title}</h4>
                                <p>{memory.content}</p>
                            </div>
                        ))
                    ) : (
                        <p>Bu kullanıcı henüz anı paylaşmamış.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProfilePublicPage;
