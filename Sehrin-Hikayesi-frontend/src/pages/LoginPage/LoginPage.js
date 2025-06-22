
import React, { useState, useContext } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './LoginPage.css';
import MotifDecor from '../../components/MotifDecor';
import axios from 'axios';
import { UserContext } from '../../context/UserContext';
import { FaArrowLeft } from 'react-icons/fa';

const LoginPage = () => {
    const { setUser } = useContext(UserContext);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    const handleLogin = async () => {
        try {
            const res = await axios.post('http://localhost:8585/api/auth/login', {
                username, password
            });
            const { token, role, userId  } = res.data;

            const normalizedRole = role.startsWith("ROLE_") ? role.replace("ROLE_", "") : role;

            localStorage.setItem("token", token);
            localStorage.setItem("role", normalizedRole);
            localStorage.setItem("userId", userId);
            localStorage.setItem("username", username);


            //  Expiration'ı JWT'den al ve kaydet
            const payloadBase64 = token.split('.')[1];
            const decodedPayload = JSON.parse(atob(payloadBase64));
            const expirationTime = decodedPayload.exp * 1000;
            localStorage.setItem("expiration", expirationTime.toString());

            setUser({ token, role: normalizedRole, username });

            if (normalizedRole === "ADMIN") {
                navigate("/admin");
            } else {
                navigate("/map");
            }

        } catch (err) {
            alert("Giriş başarısız. Bilgileri kontrol edin.");
        }
     };

    // 🔍 Eğer URL'de ?role=admin varsa kayıt ol butonunu gizle
    const isAdminLogin = new URLSearchParams(location.search).get("role") === "admin";

    return (
        <>
            <MotifDecor />
            <div className="login-container">
                <img src={require('../../assets/sehrin-hikayesi-logo.png')} alt="logo" className="entry-logo" />
                <div className="form-group">
                    <input type="text" placeholder="Kullanıcı adı" value={username} onChange={(e) => setUsername(e.target.value)} />
                </div>
                <div className="form-group password-group">
                    <input type={showPassword ? "text" : "password"} placeholder="Şifre" value={password} onChange={(e) => setPassword(e.target.value)} />
                    <span onClick={() => setShowPassword(!showPassword)} className="eye-icon">👁</span>
                </div>
                <button className="login-button" onClick={handleLogin}>GİRİŞ YAP</button>

                {!isAdminLogin && (
                    <button className="register-button" onClick={() => navigate('/register')}>KAYIT OL</button>
                )}

                <button className="back-button" onClick={() => navigate('/')}>
                    <FaArrowLeft style={{ marginRight: '8px' }} />
                    GERİ DÖN
                </button>
            </div>
        </>
    );
};

export default LoginPage;
