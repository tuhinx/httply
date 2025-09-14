import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import Introduction from './pages/Introduction';
import Declarations from './pages/Declarations';
import Configuration from './pages/Configuration';
import Download from './pages/Download';
import Contributing from './pages/Contributing';
import './App.css';

function App() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const closeSidebar = () => {
    setIsSidebarOpen(false);
  };

  return (
    <Router>
      <div className="App">
        <Header isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
        {isSidebarOpen && <div className="sidebar-overlay" onClick={closeSidebar}></div>}
        <div className="layout">
          <Sidebar isOpen={isSidebarOpen} onClose={closeSidebar} />
          <main className="main-content">
            <Routes>
              <Route path="/" element={<Introduction />} />
              <Route path="/introduction" element={<Introduction />} />
              <Route path="/declarations" element={<Declarations />} />
              <Route path="/configuration" element={<Configuration />} />
              <Route path="/download" element={<Download />} />
              <Route path="/contributing" element={<Contributing />} />
            </Routes>
          </main>
        </div>
      </div>
    </Router>
  );
}

export default App;
