import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Github, Sun, Moon, Menu, X } from 'lucide-react';
import './Header.css';

const Header = ({ isSidebarOpen, toggleSidebar }) => {
  const [isDark, setIsDark] = useState(true);

  useEffect(() => {
    // Check for saved theme preference or default to dark
    const savedTheme = localStorage.getItem('theme');
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    const shouldBeDark = savedTheme ? savedTheme === 'dark' : prefersDark;
    
    setIsDark(shouldBeDark);
    document.documentElement.setAttribute('data-theme', shouldBeDark ? 'dark' : 'light');
  }, []);

  const toggleTheme = () => {
    const newTheme = !isDark;
    setIsDark(newTheme);
    localStorage.setItem('theme', newTheme ? 'dark' : 'light');
    document.documentElement.setAttribute('data-theme', newTheme ? 'dark' : 'light');
  };

  return (
    <header className="header">
      <div className="header-container">
        <div className="header-left">
          <button className="mobile-menu-btn" onClick={toggleSidebar} title="Toggle menu">
            {isSidebarOpen ? <X size={20} /> : <Menu size={20} />}
          </button>
          <Link to="/" className="logo">
            Httply
          </Link>
        </div>
        <div className="header-right">
          <button className="theme-btn" onClick={toggleTheme} title="Toggle theme">
            {isDark ? <Sun size={16} /> : <Moon size={16} />}
          </button>
          <a href="https://github.com/tuhinx/httply" target="_blank" rel="noopener noreferrer" className="github-btn" title="GitHub">
            <Github size={16} />
          </a>
        </div>
      </div>
    </header>
  );
};

export default Header;