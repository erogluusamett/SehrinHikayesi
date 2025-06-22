
import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import ExplorePage from './pages/ExplorePage/ExplorePage';
import EntryPage from './pages/EntryPage/EntryPage';
import LoginPage from './pages/LoginPage/LoginPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import MapPage from './pages/MapPage/MapPage';
import FeedPage from './pages/FeedPage/FeedPage';

import SidebarLayout from './layout/SidebarLayout';
import AdminLayout from './layout/AdminLayout';

import UserManagementPage from './pages/AdminPage/UserManagementPage';
import CategoryManagementPage from './pages/AdminPage/CategoryManagementPage';
import TagManagementPage from './pages/AdminPage/TagManagementPage';
import StoryManagementPage from './pages/AdminPage/StoryManagementPage';
import AdminDashboardPage from './pages/AdminPage/AdminDashboardPage';
import StatsDashboardPage from './pages/AdminPage/StatsDashboardPage';
import StoryDetailPage from './pages/AdminPage/StoryDetailPage';

import { UserProvider } from './context/UserContext';
import ProtectedRoute from './components/ProtectedRoute';
import ProfilePage from './pages/ProfilePage/ProfilePage';
import ProfilePublicPage from './pages/ProfilePage/ProfilePublicPage';
import AllMemoriesByUserPage from './pages/FeedPage/AllMemoriesByUserPage';
// import MemoriesByCategoryPage from './pages/FeedPage/MemoriesByCategoryPage';
import MemoriesByTagPage from './pages/FeedPage/MemoriesByTagPage';


function TokenChecker() {
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const checkToken = () => {
            const expiration = localStorage.getItem("expiration");
            if (expiration && Date.now() > parseInt(expiration, 10)) {
                localStorage.clear();
                toast.warning("Oturum süresi doldu. Lütfen tekrar giriş yapın.");
                navigate("/login");
            }
        };

        checkToken(); // Sayfa yüklenince kontrol et
        const interval = setInterval(checkToken, 15000); // 15 saniyede bir kontrol
        return () => clearInterval(interval);
    }, [navigate, location]);

    return null;
}

function App() {
    return (
        <UserProvider>
            <Router>
                <TokenChecker />

                <Routes>
                    <Route path="/" element={<EntryPage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/map" element={<MapPage />} />
                    <Route path="/memories-by-tag/:tagName" element={<MemoriesByTagPage />} />


                    {/* Ziyaretçilere açık: Explore */}
                    <Route
                        path="/explore"
                        element={
                            <ProtectedRoute allowGuest={true}>
                                <SidebarLayout />
                            </ProtectedRoute>
                        }
                    >
                        <Route index element={<ExplorePage />} />
                    </Route>

                    {/*  Ziyaretçilere açık: Profil ve Anılar */}
                    <Route path="/profile/:username" element={<ProfilePublicPage />} />
                    <Route path="/memories-by-user/:username" element={<AllMemoriesByUserPage />} />

                    {/* Etikete göre filtreleme (zorunlu değilse guest de görebilir) */}
                    <Route
                        path="/memories"
                        element={
                            <ProtectedRoute allowGuest={true}>   {/*  ziyaretçilere açık */}
                                <SidebarLayout />
                            </ProtectedRoute>
                        }
                    >
                        <Route index element={<FeedPage />} />
                    </Route>


                    {/*  Sadece giriş yapan kullanıcılar için: memories */}
                    <Route
                        path="/memories"
                        element={
                            <ProtectedRoute>
                                <SidebarLayout />
                            </ProtectedRoute>
                        }
                    >
                        <Route index element={<FeedPage />} />
                    </Route>

                    {/* 🔐 Sadece giriş yapan kullanıcılar için: profil */}
                    <Route
                        path="/profile"
                        element={
                            <ProtectedRoute>
                                <SidebarLayout />
                            </ProtectedRoute>
                        }
                    >
                        <Route index element={<ProfilePage />} />
                    </Route>

                    {/* 👮 Sadece ADMIN için: admin paneli */}
                    <Route
                        path="/admin"
                        element={
                            <ProtectedRoute requiredRole="ADMIN">
                                <AdminLayout />
                            </ProtectedRoute>
                        }
                    >
                        <Route index element={<AdminDashboardPage />} />
                        <Route path="users" element={<UserManagementPage />} />
                        <Route path="stories" element={<StoryManagementPage />} />
                        <Route path="categories" element={<CategoryManagementPage />} />
                        <Route path="tags" element={<TagManagementPage />} />
                        <Route path="stats" element={<StatsDashboardPage />} />
                        <Route path="story/:id" element={<StoryDetailPage />} />
                    </Route>

                </Routes>

                <ToastContainer
                    position="top-center"
                    autoClose={3000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                    theme="dark"
                />
            </Router>
        </UserProvider>
    );
}

export default App;

