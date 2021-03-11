import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './reseller.reducer';
import { IReseller } from 'app/shared/model/reseller.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResellerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResellerDetail = (props: IResellerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { resellerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.reseller.detail.title">Reseller</Translate> [<b>{resellerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="resellerId">
              <Translate contentKey="dashboardApp.reseller.resellerId">Reseller Id</Translate>
            </span>
          </dt>
          <dd>{resellerEntity.resellerId}</dd>
          <dt>
            <span id="adminId">
              <Translate contentKey="dashboardApp.reseller.adminId">Admin Id</Translate>
            </span>
          </dt>
          <dd>{resellerEntity.adminId}</dd>
          <dt>
            <span id="resellerName">
              <Translate contentKey="dashboardApp.reseller.resellerName">Reseller Name</Translate>
            </span>
          </dt>
          <dd>{resellerEntity.resellerName}</dd>
          <dt>
            <span id="resellerEmail">
              <Translate contentKey="dashboardApp.reseller.resellerEmail">Reseller Email</Translate>
            </span>
          </dt>
          <dd>{resellerEntity.resellerEmail}</dd>
          <dt>
            <span id="resellerPassword">
              <Translate contentKey="dashboardApp.reseller.resellerPassword">Reseller Password</Translate>
            </span>
          </dt>
          <dd>{resellerEntity.resellerPassword}</dd>
          <dt>
            <span id="resellerCreatedAt">
              <Translate contentKey="dashboardApp.reseller.resellerCreatedAt">Reseller Created At</Translate>
            </span>
          </dt>
          <dd>
            {resellerEntity.resellerCreatedAt ? (
              <TextFormat value={resellerEntity.resellerCreatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="resellerModifiedAt">
              <Translate contentKey="dashboardApp.reseller.resellerModifiedAt">Reseller Modified At</Translate>
            </span>
          </dt>
          <dd>
            {resellerEntity.resellerModifiedAt ? (
              <TextFormat value={resellerEntity.resellerModifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dashboardApp.reseller.admin">Admin</Translate>
          </dt>
          <dd>{resellerEntity.admin ? resellerEntity.admin.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reseller" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reseller/${resellerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ reseller }: IRootState) => ({
  resellerEntity: reseller.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResellerDetail);
