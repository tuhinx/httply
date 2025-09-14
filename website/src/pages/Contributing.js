import React from 'react';
import './Contributing.css';

const Contributing = () => {
  return (
    <div className="contributing-page">
      <div className="container">
        <div className="page-header">
          <h1>Contributing</h1>
          <p>Help make Httply better for everyone</p>
        </div>

        <section className="content-section">
        <h2>How to Contribute</h2>
        <p>
          We welcome contributions from the community! Whether you're fixing bugs, 
          adding features, or improving documentation, your help is appreciated.
        </p>

        <div className="contribution-types">
          <div className="contribution-card">
            <h3>🐛 Bug Reports</h3>
            <p>Found a bug? Help us fix it by reporting it with detailed information.</p>
            <ul>
              <li>Describe the bug clearly</li>
              <li>Include steps to reproduce</li>
              <li>Provide error logs if available</li>
              <li>Specify your environment details</li>
            </ul>
          </div>

          <div className="contribution-card">
            <h3>✨ Feature Requests</h3>
            <p>Have an idea for a new feature? We'd love to hear about it!</p>
            <ul>
              <li>Describe the feature clearly</li>
              <li>Explain the use case</li>
              <li>Consider implementation complexity</li>
              <li>Check if it aligns with project goals</li>
            </ul>
          </div>

          <div className="contribution-card">
            <h3>📚 Documentation</h3>
            <p>Help improve our documentation and examples.</p>
            <ul>
              <li>Fix typos and grammar</li>
              <li>Add missing examples</li>
              <li>Improve clarity and structure</li>
              <li>Add new tutorials</li>
            </ul>
          </div>

          <div className="contribution-card">
            <h3>💻 Code Contributions</h3>
            <p>Submit pull requests with bug fixes or new features.</p>
            <ul>
              <li>Follow coding standards</li>
              <li>Add tests for new code</li>
              <li>Update documentation</li>
              <li>Ensure all tests pass</li>
            </ul>
          </div>
        </div>

        <h2>Getting Started</h2>
        <div className="getting-started">
          <div className="step">
            <h3>1. Fork the Repository</h3>
            <p>Fork the Httply repository on GitHub to your own account.</p>
            <div className="code-example">
              <pre><code>{`# Clone your fork
git clone https://github.com/YOUR_USERNAME/httply.git
cd httply

# Add upstream remote
git remote add upstream https://github.com/tuhinx/httply.git`}</code></pre>
            </div>
          </div>

          <div className="step">
            <h3>2. Create a Branch</h3>
            <p>Create a new branch for your contribution.</p>
            <div className="code-example">
              <pre><code>{`# Create and switch to new branch
git checkout -b feature/your-feature-name

# Or for bug fixes
git checkout -b fix/issue-description`}</code></pre>
            </div>
          </div>

          <div className="step">
            <h3>3. Make Your Changes</h3>
            <p>Make your changes and ensure they follow our coding standards.</p>
            <div className="code-example">
              <pre><code>{`# Make your changes
# Add tests if applicable
# Update documentation

# Stage your changes
git add .

# Commit with descriptive message
git commit -m "Add: brief description of changes"`}</code></pre>
            </div>
          </div>

          <div className="step">
            <h3>4. Submit a Pull Request</h3>
            <p>Push your changes and create a pull request.</p>
            <div className="code-example">
              <pre><code>{`# Push to your fork
git push origin feature/your-feature-name

# Then create a PR on GitHub`}</code></pre>
            </div>
          </div>
        </div>

        <h2>Coding Standards</h2>
        <p>Please follow these coding standards when contributing:</p>

        <div className="standards-list">
          <div className="standard-item">
            <h3>Kotlin Style</h3>
            <ul>
              <li>Follow Kotlin coding conventions</li>
              <li>Use meaningful variable and function names</li>
              <li>Add KDoc comments for public APIs</li>
              <li>Keep functions focused and small</li>
            </ul>
          </div>

          <div className="standard-item">
            <h3>Testing</h3>
            <ul>
              <li>Write unit tests for new functionality</li>
              <li>Ensure all existing tests pass</li>
              <li>Test edge cases and error conditions</li>
              <li>Maintain good test coverage</li>
            </ul>
          </div>

          <div className="standard-item">
            <h3>Documentation</h3>
            <ul>
              <li>Update README if needed</li>
              <li>Add code examples for new features</li>
              <li>Update API documentation</li>
              <li>Write clear commit messages</li>
            </ul>
          </div>
        </div>

        <h2>Issue Labels</h2>
        <p>We use labels to categorize issues and pull requests:</p>

        <div className="labels-grid">
          <div className="label-item">
            <span className="label bug">bug</span>
            <p>Something isn't working</p>
          </div>
          <div className="label-item">
            <span className="label enhancement">enhancement</span>
            <p>New feature or request</p>
          </div>
          <div className="label-item">
            <span className="label documentation">documentation</span>
            <p>Improvements or additions to documentation</p>
          </div>
          <div className="label-item">
            <span className="label good-first-issue">good first issue</span>
            <p>Good for newcomers</p>
          </div>
          <div className="label-item">
            <span className="label help-wanted">help wanted</span>
            <p>Extra attention is needed</p>
          </div>
          <div className="label-item">
            <span className="label question">question</span>
            <p>Further information is requested</p>
          </div>
        </div>

        <h2>Community Guidelines</h2>
        <div className="guidelines">
          <h3>Be Respectful</h3>
          <p>Treat everyone with respect and kindness. We're all here to learn and help each other.</p>

          <h3>Be Constructive</h3>
          <p>Provide constructive feedback and suggestions. Focus on the code, not the person.</p>

          <h3>Be Patient</h3>
          <p>Maintainers are volunteers. Please be patient with response times and review processes.</p>

          <h3>Be Clear</h3>
          <p>Write clear, descriptive issue reports and pull request descriptions.</p>
        </div>

        <h2>Questions?</h2>
        <p>
          If you have any questions about contributing, feel free to:
        </p>
        <ul>
          <li>Open an issue with the "question" label</li>
          <li>Start a discussion in our GitHub Discussions</li>
          <li>Contact the maintainers directly</li>
        </ul>

        <div className="contact-links">
          <a href="https://github.com/tuhinx/httply/issues" target="_blank" rel="noopener noreferrer" className="contact-link">
            <span className="contact-icon">🐛</span>
            <span>Report Issues</span>
          </a>
          <a href="https://github.com/tuhinx/httply/discussions" target="_blank" rel="noopener noreferrer" className="contact-link">
            <span className="contact-icon">💬</span>
            <span>Start Discussion</span>
          </a>
          <a href="https://github.com/tuhinx/httply" target="_blank" rel="noopener noreferrer" className="contact-link">
            <span className="contact-icon">📚</span>
            <span>View Repository</span>
          </a>
        </div>
        </section>
      </div>
    </div>
  );
};

export default Contributing;
