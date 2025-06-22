import React, { useState } from 'react';
import axios from 'axios';
import { FaCamera } from 'react-icons/fa';
import './EditProfileModal.css';

const EditProfileModal = ({ user, onClose, onUpdate }) => {
    const [username, setUsername] = useState(user.username);
    const [image, setImage] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");
        const formData = new FormData();
        formData.append("username", username);
        if (image) formData.append("image", image);

        try {
            const res = await axios.put("http://localhost:8585/api/users/update-profile", formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (res.data.profileImage) {
                localStorage.setItem("profileImage", res.data.profileImage);
            }

            onUpdate(res.data);
            window.location.reload();
        } catch (err) {
            console.error("Profil güncellenemedi", err);
        }
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setImage(file);
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h3>Profili Düzenle</h3>
                <form onSubmit={handleSubmit}>
                    <label>Kullanıcı Adı:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />

                    <label>Profil Fotoğrafı:</label>
                    <div className="photo-upload">
                        <input
                            id="image-upload"
                            type="file"
                            accept="image/*"
                            onChange={handleFileChange}
                        />
                        <label htmlFor="image-upload" className="icon-only-upload">
                            <FaCamera className="camera-icon"/>
                        </label>

                    </div>

                    <button type="submit">Kaydet</button>
                    <button type="button" onClick={onClose}>İptal</button>
                </form>
            </div>
        </div>
    );
};

export default EditProfileModal;
