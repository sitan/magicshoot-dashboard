import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvoiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceDetail = (props: IInvoiceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { invoiceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.invoice.detail.title">Invoice</Translate> [<b>{invoiceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="invoiceId">
              <Translate contentKey="dashboardApp.invoice.invoiceId">Invoice Id</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceId}</dd>
          <dt>
            <span id="contactId">
              <Translate contentKey="dashboardApp.invoice.contactId">Contact Id</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.contactId}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="dashboardApp.invoice.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.companyId}</dd>
          <dt>
            <span id="invoiceNumber">
              <Translate contentKey="dashboardApp.invoice.invoiceNumber">Invoice Number</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceNumber}</dd>
          <dt>
            <span id="orderNumber">
              <Translate contentKey="dashboardApp.invoice.orderNumber">Order Number</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.orderNumber}</dd>
          <dt>
            <span id="orderDateTime">
              <Translate contentKey="dashboardApp.invoice.orderDateTime">Order Date Time</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.orderDateTime}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="dashboardApp.invoice.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.createdAt ? <TextFormat value={invoiceEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiedAt">
              <Translate contentKey="dashboardApp.invoice.modifiedAt">Modified At</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.modifiedAt ? <TextFormat value={invoiceEntity.modifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="dashboardApp.invoice.quote">Quote</Translate>
          </dt>
          <dd>{invoiceEntity.quote ? invoiceEntity.quote.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ invoice }: IRootState) => ({
  invoiceEntity: invoice.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceDetail);
