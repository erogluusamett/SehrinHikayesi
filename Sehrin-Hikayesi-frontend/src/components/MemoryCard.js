import React from 'react';
import './MemoryCard.css';

const MemoryCard = ({ memory }) => {
    return (
        <div className="memory-card">
            <img src={memory.imageUrl} alt="anı" className="memory-img" />
            <div className="memory-info">
                <h4>{memory.title}</h4>
                <p>{memory.content}</p>
            </div>
        </div>
    );
};

export default MemoryCard;
