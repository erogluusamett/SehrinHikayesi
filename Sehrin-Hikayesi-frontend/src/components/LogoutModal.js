import React from 'react';
import './LogoutModal.css';

const LogoutModal = ({ onConfirm, onCancel }) => {
    return (
        <div className="logout-overlay">
            <div className="logout-modal">
                <p><strong>Çıkış yapmak istediğinize emin misiniz?</strong></p>
                <div className="logout-buttons">
                    <button className="logout-confirm" onClick={onConfirm}>Evet</button>
                    <button className="logout-cancel" onClick={onCancel}>Hayır</button>
                </div>
            </div>
        </div>
    );
};

export default LogoutModal;
