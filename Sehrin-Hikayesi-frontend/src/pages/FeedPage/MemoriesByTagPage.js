import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import PostCard from '../../components/PostCard'; // Doğru dizine göre güncelle

const MemoriesByTagPage = () => {
    const { tagName } = useParams();
    const [memories, setMemories] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8585/api/stories/by-tag/${tagName}`)
            .then(res => setMemories(res.data))
            .catch(err => console.error(err));
    }, [tagName]);

    return (
        <div className="feed-page">
            <h2 style={{ color: '#1C2C48', marginBottom: '20px' }}>#{tagName} Etiketli Anılar</h2>
            <div className="post-list">
                {memories.map((post) => (
                    <PostCard key={post.id} post={post} />
                ))}
            </div>
        </div>
    );
};

export default MemoriesByTagPage;
