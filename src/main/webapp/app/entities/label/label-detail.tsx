import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './label.reducer';
import { ILabel } from 'app/shared/model/label.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILabelDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LabelDetail = (props: ILabelDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { labelEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.label.detail.title">Label</Translate> [<b>{labelEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="labelId">
              <Translate contentKey="dashboardApp.label.labelId">Label Id</Translate>
            </span>
          </dt>
          <dd>{labelEntity.labelId}</dd>
          <dt>
            <span id="labelName">
              <Translate contentKey="dashboardApp.label.labelName">Label Name</Translate>
            </span>
          </dt>
          <dd>{labelEntity.labelName}</dd>
          <dt>
            <span id="labelType">
              <Translate contentKey="dashboardApp.label.labelType">Label Type</Translate>
            </span>
          </dt>
          <dd>{labelEntity.labelType}</dd>
          <dt>
            <span id="labelWidth">
              <Translate contentKey="dashboardApp.label.labelWidth">Label Width</Translate>
            </span>
          </dt>
          <dd>{labelEntity.labelWidth}</dd>
          <dt>
            <span id="labelHeight">
              <Translate contentKey="dashboardApp.label.labelHeight">Label Height</Translate>
            </span>
          </dt>
          <dd>{labelEntity.labelHeight}</dd>
        </dl>
        <Button tag={Link} to="/label" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/label/${labelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ label }: IRootState) => ({
  labelEntity: label.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LabelDetail);
