import { useContext } from 'react';
import AuthContext from './AuthContextProvider';
import { Navigate } from 'react-router-dom';
import { PropTypes } from 'prop-types';

export const ProtectedRoute = ({ children, accessBy }) => {
  const { user } = useContext(AuthContext);

  if (accessBy == 'non-authenticated') {
    if (!user) {
      return children;
    }
  } else if (accessBy == 'authenticated') {
    if (user) {
      return children;
    }
  }
  return <Navigate to="/"></Navigate>;
};

ProtectedRoute.propTypes = {
  accessBy: PropTypes.oneOf(['non-authenticated', 'authenticated']).isRequired,
  children: PropTypes.node.isRequired,
};
