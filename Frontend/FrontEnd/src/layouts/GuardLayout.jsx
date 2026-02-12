import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/axios";
import { useAuth } from "../contexts/AuthContext";
import "./GuardLayout.css";

export default function GuardLayout({ children }) {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const [insideCount, setInsideCount] = useState(0);

  const isActive = (path) => location.pathname === path;

  useEffect(() => {
    const fetchCount = async () => {
      try {
        const res = await api.get("/api/visitors/inside");
        setInsideCount(res.data.length);
      } catch (e) {
        console.error(e);
      }
    };

    fetchCount();
    const interval = setInterval(fetchCount, 10000);
    return () => clearInterval(interval);
  }, []);

  const handleLogout = async () => {
    await logout();
    navigate("/", { replace: true });
  };

  return (
    <div className="guard-layout">
      <aside className="guard-sidebar">
        <div className="guard-sidebar-header">
          <h3>Guard Panel</h3>
          <p>Security Access</p>
        </div>

        <nav className="guard-nav">
          <Link to="/guard/dashboard" className={isActive("/guard/dashboard") ? "active" : ""}>
            Dashboard
          </Link>

          <Link to="/guard/add-visitor" className={isActive("/guard/add-visitor") ? "active" : ""}>
            Add Visitor
          </Link>

          <Link to="/guard/inside-visitors" className={isActive("/guard/inside-visitors") ? "active" : ""}>
            Inside Visitors
            {insideCount > 0 && <span className="count-badge">{insideCount}</span>}
          </Link>

          <Link to="/guard/today-visitors" className={isActive("/guard/today-visitors") ? "active" : ""}>
            Today Visitors
          </Link>

          <div className="divider" />

          <button className="logout-btn" onClick={handleLogout}>
            Logout
          </button>
        </nav>
      </aside>

      <div className="guard-main">
        <header className="guard-topbar">
          <div>
            <h4 className="guard-title">
              {user?.societyName || "Society Guard Dashboard"}
            </h4>

            {user && (
              <small className="guard-info">
                {user.name}
              </small>
            )}
          </div>
        </header>

        <main className="guard-content">
          {children}
        </main>
      </div>
    </div>
  );
}
