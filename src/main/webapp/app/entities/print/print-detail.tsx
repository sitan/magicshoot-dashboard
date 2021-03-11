import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './print.reducer';
import { IPrint } from 'app/shared/model/print.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPrintDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PrintDetail = (props: IPrintDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { printEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.print.detail.title">Print</Translate> [<b>{printEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="printId">
              <Translate contentKey="dashboardApp.print.printId">Print Id</Translate>
            </span>
          </dt>
          <dd>{printEntity.printId}</dd>
          <dt>
            <span id="printName">
              <Translate contentKey="dashboardApp.print.printName">Print Name</Translate>
            </span>
          </dt>
          <dd>{printEntity.printName}</dd>
          <dt>
            <span id="printType">
              <Translate contentKey="dashboardApp.print.printType">Print Type</Translate>
            </span>
          </dt>
          <dd>{printEntity.printType}</dd>
          <dt>
            <span id="printWidth">
              <Translate contentKey="dashboardApp.print.printWidth">Print Width</Translate>
            </span>
          </dt>
          <dd>{printEntity.printWidth}</dd>
          <dt>
            <span id="printHeight">
              <Translate contentKey="dashboardApp.print.printHeight">Print Height</Translate>
            </span>
          </dt>
          <dd>{printEntity.printHeight}</dd>
        </dl>
        <Button tag={Link} to="/print" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/print/${printEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ print }: IRootState) => ({
  printEntity: print.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PrintDetail);
