import React from 'react';
import './AdminDashboardPage.css';

const AdminDashboardPage = () => {
    return (
        <div className="admin-dashboard">
            <div className="main-content">
                {/* Başlık */}
                <h2 className="admin-title">Admin Paneli</h2>

                {/* Açıklama metni */}
                <p className="welcome-msg">
                    Hoş geldiniz! İstediğiniz işlemi sol panelden seçiniz.
                </p>

                {/* Sol panel açıklamaları */}
                <div className="panel-features">
                    <div className="feature-item">
                        <h3>Kullanıcı Yönetimi</h3>
                        <p>Kayıtlı kullanıcıları görüntüleyin, düzenleyin veya silin.</p>
                    </div>

                    <div className="feature-item">
                        <h3>Anı Yönetimi</h3>
                        <p>Paylaşılan anıları kontrol edin, düzenleyin veya kaldırın.</p>
                    </div>

                    <div className="feature-item">
                        <h3>Kategori Yönetimi</h3>
                        <p>Anılar için kategori oluşturun, düzenleyin ya da silin.</p>
                    </div>

                    <div className="feature-item">
                        <h3>Etiket Yönetimi</h3>
                        <p>Paylaşımlarda kullanılan etiketleri yönetin.</p>
                    </div>

                    <div className="feature-item">
                        <h3>İstatistikler</h3>
                        <p>Sistem genelinde kullanıcı, içerik ve etkileşim verilerini görüntüleyin.</p>
                    </div>
                </div>

            </div>


        </div>
    );
};

export default AdminDashboardPage;