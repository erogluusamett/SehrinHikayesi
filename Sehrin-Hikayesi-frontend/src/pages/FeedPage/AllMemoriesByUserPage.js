import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './AllMemoriesByUserPage.css';

const AllMemoriesByUserPage = () => {
    const { username } = useParams();
    const [memories, setMemories] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem("token");
                const res = await axios.get(`http://localhost:8585/api/stories/by-username/${username}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setMemories(res.data);
            } catch (err) {
                console.error("Anı getirilemedi:", err);
            }
        };

        fetchData();
    }, [username]);

    return (
        <div className="memories-by-user-container">
            <h2>{username} adlı kullanıcının tüm anıları</h2>

            {memories.length === 0 ? (
                <p>Bu kullanıcıya ait hiç anı bulunamadı.</p>
            ) : (
                <div className="memory-grid">
                    {memories.map((story) => (
                        <div key={story.id} className="memory-card">
                            <img
                                src={`http://localhost:8585/api/files/${story.photoId}`}
                                alt="anı"
                                className="memory-image"
                            />
                            <div className="memory-content">
                                <strong>{story.title}</strong>
                                <p>{story.content}</p>
                                <small>{story.cityName}</small>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default AllMemoriesByUserPage;
