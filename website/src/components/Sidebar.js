import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = ({ isOpen, onClose }) => {
  const location = useLocation();

  const handleLinkClick = () => {
    if (onClose) {
      onClose();
    }
  };

  return (
    <aside className={`sidebar ${isOpen ? 'sidebar-open' : ''}`}>
      <nav className="sidebar-nav">
                <div className="nav-section">
                  <span className="nav-label">Documentation</span>
                  <Link 
                    to="/introduction" 
                    className={`nav-link ${location.pathname === '/' || location.pathname === '/introduction' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Introduction
                  </Link>
                  <Link 
                    to="/retra-api" 
                    className={`nav-link ${location.pathname === '/retra-api' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Retra API
                  </Link>
                  <Link 
                    to="/voltra-api" 
                    className={`nav-link ${location.pathname === '/voltra-api' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Voltra API
                  </Link>
                  <Link 
                    to="/declarations" 
                    className={`nav-link ${location.pathname === '/declarations' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Declarations
                  </Link>
                  <Link 
                    to="/configuration" 
                    className={`nav-link ${location.pathname === '/configuration' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Configuration
                  </Link>
                  <Link 
                    to="/download" 
                    className={`nav-link ${location.pathname === '/download' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Download
                  </Link>
                  <Link 
                    to="/contributing" 
                    className={`nav-link ${location.pathname === '/contributing' ? 'active' : ''}`}
                    onClick={handleLinkClick}
                  >
                    Contributing
                  </Link>
                </div>
        <div className="nav-section">
          <span className="nav-label">Resources</span>
          <a href="https://github.com/tuhinx/httply" target="_blank" rel="noopener noreferrer" className="nav-link" onClick={handleLinkClick}>GitHub</a>
        </div>
      </nav>
    </aside>
  );
};

export default Sidebar;
