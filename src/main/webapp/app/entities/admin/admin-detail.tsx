import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './admin.reducer';
import { IAdmin } from 'app/shared/model/admin.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdminDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdminDetail = (props: IAdminDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adminEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.admin.detail.title">Admin</Translate> [<b>{adminEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="adminId">
              <Translate contentKey="dashboardApp.admin.adminId">Admin Id</Translate>
            </span>
          </dt>
          <dd>{adminEntity.adminId}</dd>
          <dt>
            <span id="adminName">
              <Translate contentKey="dashboardApp.admin.adminName">Admin Name</Translate>
            </span>
          </dt>
          <dd>{adminEntity.adminName}</dd>
          <dt>
            <span id="adminEmail">
              <Translate contentKey="dashboardApp.admin.adminEmail">Admin Email</Translate>
            </span>
          </dt>
          <dd>{adminEntity.adminEmail}</dd>
          <dt>
            <span id="adminPassword">
              <Translate contentKey="dashboardApp.admin.adminPassword">Admin Password</Translate>
            </span>
          </dt>
          <dd>{adminEntity.adminPassword}</dd>
          <dt>
            <span id="adminCreatedAt">
              <Translate contentKey="dashboardApp.admin.adminCreatedAt">Admin Created At</Translate>
            </span>
          </dt>
          <dd>
            {adminEntity.adminCreatedAt ? (
              <TextFormat value={adminEntity.adminCreatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="adminModifiedAt">
              <Translate contentKey="dashboardApp.admin.adminModifiedAt">Admin Modified At</Translate>
            </span>
          </dt>
          <dd>
            {adminEntity.adminModifiedAt ? (
              <TextFormat value={adminEntity.adminModifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/admin" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/${adminEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ admin }: IRootState) => ({
  adminEntity: admin.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdminDetail);
