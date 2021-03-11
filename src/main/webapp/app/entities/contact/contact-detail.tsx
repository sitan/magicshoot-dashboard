import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContactDetail = (props: IContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { contactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.contact.detail.title">Contact</Translate> [<b>{contactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="contactId">
              <Translate contentKey="dashboardApp.contact.contactId">Contact Id</Translate>
            </span>
          </dt>
          <dd>{contactEntity.contactId}</dd>
          <dt>
            <span id="contactName">
              <Translate contentKey="dashboardApp.contact.contactName">Contact Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.contactName}</dd>
          <dt>
            <span id="companyId">
              <Translate contentKey="dashboardApp.contact.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{contactEntity.companyId}</dd>
          <dt>
            <span id="contactTelephone">
              <Translate contentKey="dashboardApp.contact.contactTelephone">Contact Telephone</Translate>
            </span>
          </dt>
          <dd>{contactEntity.contactTelephone}</dd>
          <dt>
            <span id="contactEmail">
              <Translate contentKey="dashboardApp.contact.contactEmail">Contact Email</Translate>
            </span>
          </dt>
          <dd>{contactEntity.contactEmail}</dd>
          <dt>
            <span id="contactPassword">
              <Translate contentKey="dashboardApp.contact.contactPassword">Contact Password</Translate>
            </span>
          </dt>
          <dd>{contactEntity.contactPassword}</dd>
          <dt>
            <span id="contactCreatedAt">
              <Translate contentKey="dashboardApp.contact.contactCreatedAt">Contact Created At</Translate>
            </span>
          </dt>
          <dd>
            {contactEntity.contactCreatedAt ? (
              <TextFormat value={contactEntity.contactCreatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contactModifiedAt">
              <Translate contentKey="dashboardApp.contact.contactModifiedAt">Contact Modified At</Translate>
            </span>
          </dt>
          <dd>
            {contactEntity.contactModifiedAt ? (
              <TextFormat value={contactEntity.contactModifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dashboardApp.contact.company">Company</Translate>
          </dt>
          <dd>{contactEntity.company ? contactEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ contact }: IRootState) => ({
  contactEntity: contact.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContactDetail);
