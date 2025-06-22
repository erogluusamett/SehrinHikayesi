import { useContext, useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { UserContext } from "../context/UserContext";
import { toast } from "react-toastify";

const ProtectedRoute = ({ children, requiredRole, allowGuest = false }) => {
    const { user } = useContext(UserContext);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const timeout = setTimeout(() => setLoading(false), 100);
        return () => clearTimeout(timeout);
    }, []);

    if (loading) return null;

    //  Ziyaretçi ama erişimine izin verilmişse (örneğin Explore)
    if (!user && allowGuest) return children;

    //  Ziyaretçi ama erişimine izin VERİLMEMİŞSE
    if (!user) {
        toast.warning("Bu sayfayı görüntülemek için giriş yapmalısınız.");
        return <Navigate to="/login" replace />;
    }

    //  Rol uyuşmazlığı varsa (örneğin admin yetkisi yok)
    if (requiredRole && user.role !== requiredRole) {
        toast.error("Bu sayfaya erişim yetkiniz yok.");
        return <Navigate to="/" replace />;
    }

    return children;
};

export default ProtectedRoute;
