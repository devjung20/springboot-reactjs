import { toast } from 'react-toastify';
import jwtInterceptor from '../shared/jwtInterceptor';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useState } from 'react';
import { PropTypes } from 'prop-types';

export const CategoryDelete = ({ onDelete, itemId }) => {
  const [showModal, setShowModal] = useState(false);

  const handleDelete = async () => {
    try {
      await jwtInterceptor.delete(`http://localhost:2000/category/delete/${itemId}`);
      setShowModal(false);
      onDelete(itemId);
      toast.success('Deleted successfully');
    } catch (error) {
      console.error('Error deleting item: ' + error);
      toast.error('Error deleting item');
    }
  };

  return (
    <div>
      <Button variant="danger" onClick={() => setShowModal(true)}>
        Delete
      </Button>
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Deletion</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete this item?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleDelete}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};
CategoryDelete.propTypes = {
  onDelete: PropTypes.func.isRequired,
  itemId: PropTypes.any.isRequired,
};
