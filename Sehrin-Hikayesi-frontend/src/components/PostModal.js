import React, { useEffect, useState, useCallback } from 'react';
import './PostModal.css';
import { FaRegHeart, FaHeart, FaRegComment } from 'react-icons/fa';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

const PostModal = ({ post, onClose }) => {
    const [comment, setComment] = useState("");
    const [comments, setComments] = useState([]);
    const [liked, setLiked] = useState(false);
    const [likeCount, setLikeCount] = useState(0);
    const [likers, setLikers] = useState([]);
    const [showLikers, setShowLikers] = useState(false);

    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    const fetchComments = useCallback(async () => {
        try {
            const res = await axios.get(`http://localhost:8585/api/comments/story/${post.id}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setComments(res.data);
        } catch {
            toast.error("Yorumlar yüklenemedi.");
        }
    }, [post.id, token]);

    useEffect(() => {
        const fetchLikeStatusAndCount = async () => {
            try {
                const [likeStatusRes, likeCountRes] = await Promise.all([
                    axios.get("http://localhost:8585/api/likes/exists", {
                        params: { userId, storyId: post.id },
                        headers: { Authorization: `Bearer ${token}` }
                    }),
                    axios.get("http://localhost:8585/api/likes/count", {
                        params: { storyId: post.id },
                        headers: { Authorization: `Bearer ${token}` }
                    })
                ]);
                setLiked(likeStatusRes.data);
                setLikeCount(likeCountRes.data);
            } catch {
                toast.error("Beğeni bilgileri yüklenemedi.");
            }
        };

        fetchLikeStatusAndCount();
        fetchComments();
    }, [post.id, userId, token, fetchComments]);

    const handleLikeToggle = async () => {
        try {
            if (!liked) {
                await axios.post("http://localhost:8585/api/likes/create", {
                    userId,
                    storyId: post.id
                }, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setLiked(true);
                setLikeCount(prev => prev + 1);
            } else {
                await axios.delete("http://localhost:8585/api/likes/unlike", {
                    params: { userId, storyId: post.id },
                    headers: { Authorization: `Bearer ${token}` }
                });
                setLiked(false);
                setLikeCount(prev => prev - 1);
            }
        } catch {
            toast.error("Beğeni işlemi başarısız.");
        }
    };

    const handleShowLikers = async () => {
        try {
            const res = await axios.get("http://localhost:8585/api/likes/users", {
                params: { storyId: post.id },
                headers: { Authorization: `Bearer ${token}` }
            });
            setLikers(res.data);
            setShowLikers(true);
        } catch {
            toast.error("Beğenenler yüklenemedi.");
        }
    };

    const handleCommentSubmit = async () => {
        try {
            await axios.post("http://localhost:8585/api/comments", {
                content: comment,
                userId,
                storyId: post.id
            }, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setComment("");
            fetchComments();
        } catch {
            toast.error("Yorum gönderilemedi.");
        }
    };

    const formattedDate = post.createdAt || "Tarih bilinmiyor";

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="modal-close" onClick={onClose}>×</button>

                <div className="modal-left">
                    <img src={post.imageUrl} alt={post.title} />
                </div>

                <div className="modal-right">
                    <div>
                        <p className="post-meta">
                            <strong>Şehir:</strong> {post.city} <br />
                            <strong>Kategori:</strong> {post.category || "Yok"}
                        </p>

                        <p className="post-author"><strong>{post.authorUsername}</strong></p>
                        <p className="post-description">{post.content}</p>

                        <div className="like-comment-bar">
                            <span onClick={handleLikeToggle}>
                                {liked ? <FaHeart color="red" /> : <FaRegHeart />}
                            </span>
                            <FaRegComment style={{ marginLeft: 10 }} />
                        </div>

                        <div className="interaction-counts">
                            <span className="clickable-count" onClick={handleShowLikers}>
                                {likeCount} beğeni
                            </span>
                            <span className="divider">•</span>
                            <span>{comments.length} yorum</span>
                        </div>

                        <p className="post-date">{formattedDate}</p>
                    </div>

                    <div>
                        <div className="comment-input-group">
                            <input
                                className="comment-input"
                                placeholder="Yorum ekle..."
                                value={comment}
                                onChange={(e) => setComment(e.target.value)}
                            />
                            <button
                                className="comment-send"
                                onClick={handleCommentSubmit}
                                disabled={!comment.trim()}
                            >
                                Gönder
                            </button>
                        </div>

                        <div className="comment-list">
                            {comments.map((c, i) => (
                                <div className="comment-item" key={i}>
                                    <span
                                        className="comment-username"
                                        onClick={() => navigate(`/profile/${c.username}`)}
                                    >
                                        {c.username}
                                    </span>
                                    {c.content}
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>

            {showLikers && (
                <div className="likers-popup">
                    <h4 style={{ textAlign: "center" }}>Beğenenler</h4>
                    <ul>
                        {likers.map((username, i) => (
                            <li key={i}>
                                <span
                                    onClick={() => navigate(`/profile/${username}`)}
                                    style={{ cursor: 'pointer', color: 'black' }}
                                >
                                    👤 {username}
                                </span>
                            </li>
                        ))}
                    </ul>
                    <button onClick={() => setShowLikers(false)} style={{ display: "block", margin: "10px auto" }}>
                        Kapat
                    </button>
                </div>
            )}
        </div>
    );
};

export default PostModal;
