import React, { useState } from 'react';
import './SearchBar.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SearchBar = ({ onClose }) => {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const [activeTab, setActiveTab] = useState('users');
    const navigate = useNavigate();

    const handleSearch = async () => {
        if (!query) return;
        try {
            const token = localStorage.getItem("token");
            const response = await axios.get(`http://localhost:8585/api/search?q=${query}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setResults(response.data);
        } catch (error) {
            console.error("Arama hatası:", error);
        }
    };

    const filteredResults = results.filter(item => {
        if (activeTab === 'users') return item.type === 'Kullanıcı';
        if (activeTab === 'tags') return item.type === 'Etiket';
        return false;
    });

    return (
        <div className="search-overlay" onClick={onClose}>
            <div className="search-box-modal" onClick={e => e.stopPropagation()}>
                <div className="search-header">
                    <h3>Ara</h3>
                    <button className="close-btn" onClick={onClose}>×</button>
                </div>

                <div className="search-button-wrapper">
                    <button className="search-icon-button" onClick={handleSearch}>
                        🔍 Ara
                    </button>
                </div>


                <div className="search-tabs">
                    <button className={activeTab === 'users' ? 'active' : ''}
                            onClick={() => setActiveTab('users')}>Kullanıcılar
                    </button>
                    <button className={activeTab === 'tags' ? 'active' : ''}
                            onClick={() => setActiveTab('tags')}>Etiketler
                    </button>

                </div>


                <input
                    className="search-input"
                    type="text"
                    placeholder={`${activeTab} içinde ara...`}
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                />

                <ul className="search-results">
                    {filteredResults.map((item, index) => (
                        <li
                            key={index}
                            className="search-item"
                            onClick={() => {
                                if (activeTab === 'tags') {
                                    navigate(`/memories-by-tag/${encodeURIComponent(item.name.replace(/^#/, ''))}`);
                                }else if (activeTab === 'users') {
                                    navigate(`/profile/${item.username}`);
                                }
                            }}

                        >
                            <div className="search-item-title">
                                {activeTab === 'tags' && `#${item.name}`}
                                {activeTab === 'users' && `${item.username}`}
                            </div>

                        </li>
                    ))}
                </ul>

            </div>
        </div>
    );
};

export default SearchBar;
