import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './customer.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICustomerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CustomerDetail = (props: ICustomerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { customerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.customer.detail.title">Customer</Translate> [<b>{customerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="customerId">
              <Translate contentKey="dashboardApp.customer.customerId">Customer Id</Translate>
            </span>
          </dt>
          <dd>{customerEntity.customerId}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="dashboardApp.customer.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{customerEntity.companyId}</dd>
          <dt>
            <span id="contactEmail">
              <Translate contentKey="dashboardApp.customer.contactEmail">Contact Email</Translate>
            </span>
          </dt>
          <dd>{customerEntity.contactEmail}</dd>
          <dt>
            <span id="contactCreatedAt">
              <Translate contentKey="dashboardApp.customer.contactCreatedAt">Contact Created At</Translate>
            </span>
          </dt>
          <dd>
            {customerEntity.contactCreatedAt ? (
              <TextFormat value={customerEntity.contactCreatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contactModifiedAt">
              <Translate contentKey="dashboardApp.customer.contactModifiedAt">Contact Modified At</Translate>
            </span>
          </dt>
          <dd>
            {customerEntity.contactModifiedAt ? (
              <TextFormat value={customerEntity.contactModifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dashboardApp.customer.company">Company</Translate>
          </dt>
          <dd>{customerEntity.company ? customerEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ customer }: IRootState) => ({
  customerEntity: customer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CustomerDetail);
