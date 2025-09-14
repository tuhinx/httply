import React, { useState } from 'react';
import { FiCopy, FiCheck } from 'react-icons/fi';
import './CopyButton.css';

const CopyButton = ({ text, className = '' }) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(text);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch (err) {
      console.error('Failed to copy text: ', err);
    }
  };

  return (
    <button 
      className={`copy-button ${className} ${copied ? 'copied' : ''}`}
      onClick={handleCopy}
      title={copied ? 'Copied!' : 'Copy to clipboard'}
    >
      {copied ? <FiCheck size={16} /> : <FiCopy size={16} />}
      {copied && <span className="copy-tooltip">Copied!</span>}
    </button>
  );
};

export default CopyButton;
