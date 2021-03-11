import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './quote.reducer';
import { IQuote } from 'app/shared/model/quote.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuoteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuoteDetail = (props: IQuoteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { quoteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.quote.detail.title">Quote</Translate> [<b>{quoteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="quoteId">
              <Translate contentKey="dashboardApp.quote.quoteId">Quote Id</Translate>
            </span>
          </dt>
          <dd>{quoteEntity.quoteId}</dd>
          <dt>
            <span id="contactId">
              <Translate contentKey="dashboardApp.quote.contactId">Contact Id</Translate>
            </span>
          </dt>
          <dd>{quoteEntity.contactId}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="dashboardApp.quote.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{quoteEntity.companyId}</dd>
          <dt>
            <span id="quoteDescription">
              <Translate contentKey="dashboardApp.quote.quoteDescription">Quote Description</Translate>
            </span>
          </dt>
          <dd>{quoteEntity.quoteDescription}</dd>
          <dt>
            <span id="quotePrice">
              <Translate contentKey="dashboardApp.quote.quotePrice">Quote Price</Translate>
            </span>
          </dt>
          <dd>{quoteEntity.quotePrice}</dd>
          <dt>
            <span id="orderNumber">
              <Translate contentKey="dashboardApp.quote.orderNumber">Order Number</Translate>
            </span>
          </dt>
          <dd>{quoteEntity.orderNumber}</dd>
          <dt>
            <span id="quoteCreatedAt">
              <Translate contentKey="dashboardApp.quote.quoteCreatedAt">Quote Created At</Translate>
            </span>
          </dt>
          <dd>
            {quoteEntity.quoteCreatedAt ? (
              <TextFormat value={quoteEntity.quoteCreatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="quoteModifiedAt">
              <Translate contentKey="dashboardApp.quote.quoteModifiedAt">Quote Modified At</Translate>
            </span>
          </dt>
          <dd>
            {quoteEntity.quoteModifiedAt ? (
              <TextFormat value={quoteEntity.quoteModifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dashboardApp.quote.contact">Contact</Translate>
          </dt>
          <dd>{quoteEntity.contact ? quoteEntity.contact.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/quote" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/quote/${quoteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ quote }: IRootState) => ({
  quoteEntity: quote.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuoteDetail);
