import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './StoryManagementPage.css';

const StoryManagementPage = () => {
    const [stories, setStories] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [selectedStory, setSelectedStory] = useState(null); //  Detay için

    useEffect(() => {
        const fetchStories = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await axios.get(`http://localhost:8585/api/stories/paginated?page=${page}&size=10`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setStories(response.data.content);
                setTotalPages(response.data.totalPages);
            } catch (error) {
                console.error("Anılar alınamadı:", error);
            }
        };

        fetchStories();
    }, [page]);

    const handleDelete = async (storyId) => {
        if (!window.confirm("Bu anıyı silmek istediğinize emin misiniz?")) return;
        try {
            const token = localStorage.getItem("token");
            await axios.delete(`http://localhost:8585/api/stories/${storyId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });

            // Yeniden yükle
            const response = await axios.get(`http://localhost:8585/api/stories/paginated?page=${page}&size=10`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setStories(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            alert("Silme işlemi sırasında hata oluştu.");
        }
    };

    const handleView = (story) => {
        setSelectedStory(story);
    };

    return (
        <div className="story-management">
            <h2>Anı Yönetimi</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Başlık</th>
                    <th>Yazar</th>
                    <th>Şehir</th>
                    <th>Kategori</th>
                    <th>İşlem</th>
                </tr>
                </thead>
                <tbody>
                {stories.map((story) => (
                    <tr key={story.id}>
                        <td>{story.id}</td>
                        <td>{story.title}</td>
                        <td>{story.authorUsername}</td>
                        <td>{story.city}</td>
                        <td>{story.category}</td>
                        <td>
                            <button className="delete" onClick={() => handleDelete(story.id)}>Sil</button>
                            <button className="view" onClick={() => handleView(story)}>Görüntüle</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            <div className="pagination">
                {Array.from({ length: totalPages }, (_, i) => (
                    <button
                        key={i}
                        onClick={() => setPage(i)}
                        className={page === i ? 'active' : ''}
                    >
                        {i + 1}
                    </button>
                ))}
            </div>

            {/*  Modal Kart */}
            {selectedStory && (
                <div className="story-detail-modal">
                    <div className="story-detail-card">
                        <h3>{selectedStory.title}</h3>

                        {/* 📸 Görsel */}
                        {selectedStory.imageUrl && (
                            <img
                                src={selectedStory.imageUrl}
                                alt="Anı görseli"
                                className="story-detail-image"
                            />
                        )}

                        <p><strong>Yazar:</strong> {selectedStory.authorUsername}</p>
                        <p><strong>Şehir:</strong> {selectedStory.city}</p>
                        <p><strong>Kategori:</strong> {selectedStory.category}</p>
                        <p><strong>İçerik:</strong> {selectedStory.content}</p>

                        <button onClick={() => setSelectedStory(null)}>Kapat</button>
                    </div>
                </div>
            )}

        </div>
    );
};

export default StoryManagementPage;
