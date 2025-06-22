import React, { useState, useEffect } from 'react';
import './PostCard.css';
import { FaRegHeart, FaHeart, FaRegComment } from 'react-icons/fa';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useUser } from '../context/UserContext'; // 🔐

const PostCard = ({ post }) => {
    const { user } = useUser(); // 🔐

    const [showCommentInput, setShowCommentInput] = useState(false);
    const [comment, setComment] = useState("");
    const [comments, setComments] = useState([]);
    const [liked, setLiked] = useState(false);
    const [likeCount, setLikeCount] = useState(0);
    const [likers, setLikers] = useState([]);
    const [showLikers, setShowLikers] = useState(false);

    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!post?.id || !user) return;

        axios.get("http://localhost:8585/api/likes/exists", {
            params: { userId, storyId: post.id },
            headers: { Authorization: `Bearer ${token}` }
        }).then(res => setLiked(res.data));

        axios.get("http://localhost:8585/api/likes/count", {
            params: { storyId: post.id },
            headers: { Authorization: `Bearer ${token}` }

        }).then(res => setLikeCount(res.data));

        axios.get("http://localhost:8585/api/comments/story/" + post.id)
            .then(res => setComments(res.data));
    }, [post.id, userId, token, user]);

    useEffect(() => {
        if (showLikers && post?.id) {
            axios.get("http://localhost:8585/api/likes/users", {
                params: { storyId: post.id },
                headers: { Authorization: `Bearer ${token}` }
            })
                .then(res => setLikers(res.data))
                .catch(() => toast.error("Beğenenler yüklenemedi."));
        }
    }, [showLikers, post.id, token]);
    const handleLikeToggle = async () => {
        if (!user) {
            toast.info("Beğeni için giriş yapmalısınız.");
            return;
        }

        try {
            if (!liked) {
                await axios.post("http://localhost:8585/api/likes/create", {
                    userId,
                    storyId: post.id
                }, {headers: { Authorization: `Bearer ${token}` }
                });
                setLiked(true);
                setLikeCount(prev => prev + 1);
                toast.success("Beğenildi!");
            } else {
                await axios.delete("http://localhost:8585/api/likes/unlike", {
                    params: { userId, storyId: post.id },
                    headers: { Authorization: `Bearer ${token}` }

                });
                setLiked(false);
                setLikeCount(prev => prev - 1);
                toast.info("Beğeni geri alındı.");
            }
        } catch {
            toast.error("Beğeni işlemi başarısız.");
        }
    };

    const handleCommentSubmit = async () => {
        if (!comment.trim()) return;
        if (!user) {
            toast.warning("Yorum yapmak için giriş yapmalısınız.");
            return;
        }

        try {
            const res = await axios.post("http://localhost:8585/api/comments/create", {
                content: comment,
                userId: parseInt(userId),
                storyId: post.id
            }, {
                headers: { Authorization: `Bearer ${token}` }

            });

            setComments(prev => [...prev, res.data]);
            setComment("");
        } catch {
            toast.error("Yorum gönderilemedi.");
        }
    };

    return (
        <div className="post-card">
            <div className="post-header">
                <span className="username">{post.authorUsername}</span>
            </div>

            {post.imageUrl && (
                <img src={post.imageUrl} alt={post.title} className="post-image" />
            )}

            <div className="post-actions">
                <span className="icon" onClick={handleLikeToggle}>
                    {liked ? <FaHeart color="red" /> : <FaRegHeart />}
                </span>
                <FaRegComment className="icon" onClick={() => {
                    if (!user) {
                        toast.info("Yorum için giriş yapmalısınız.");
                        return;
                    }
                    setShowCommentInput(!showCommentInput);
                }} />
                <span className="like-count" onClick={() => setShowLikers(true)}>
                    {likeCount} beğeni
                </span>
            </div>

            <div className="post-caption">
                <strong>{post.title}</strong>
                <p>{post.content}</p>
            </div>

            {showCommentInput && (
                <div className="post-comment">
                    <input
                        type="text"
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        placeholder="Yorum ekle..."
                    />
                    <button onClick={handleCommentSubmit} disabled={!comment.trim()}>
                        Gönder
                    </button>

                    <div className="comment-list">
                        {comments.map(c => (
                            <div key={c.id} className="comment-item">
                                <strong>{c.username}:</strong> {c.content}
                            </div>
                        ))}
                    </div>
                </div>
            )}

            {showLikers && (
                <div className="likers-popup">
                    <h4>Beğenenler</h4>
                    <ul>
                        {likers.map((username, index) => (
                            <li key={index}>
                                <a href={`/profile/${username}`}>{username}</a>

                            </li>
                        ))}
                    </ul>
                    <button onClick={() => setShowLikers(false)}>Kapat</button>
                </div>
            )}
        </div>
    );
};

export default PostCard;
