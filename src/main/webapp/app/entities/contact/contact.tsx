import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IContactProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Contact = (props: IContactProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { contactList, match, loading } = props;
  return (
    <div>
      <h2 id="contact-heading">
        <Translate contentKey="dashboardApp.contact.home.title">Contacts</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.contact.home.createLabel">Create new Contact</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {contactList && contactList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactId">Contact Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactName">Contact Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactTelephone">Contact Telephone</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactEmail">Contact Email</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactPassword">Contact Password</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactCreatedAt">Contact Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.contactModifiedAt">Contact Modified At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.contact.company">Company</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contactList.map((contact, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${contact.id}`} color="link" size="sm">
                      {contact.id}
                    </Button>
                  </td>
                  <td>{contact.contactId}</td>
                  <td>{contact.contactName}</td>
                  <td>{contact.companyId}</td>
                  <td>{contact.contactTelephone}</td>
                  <td>{contact.contactEmail}</td>
                  <td>{contact.contactPassword}</td>
                  <td>
                    {contact.contactCreatedAt ? (
                      <TextFormat type="date" value={contact.contactCreatedAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {contact.contactModifiedAt ? (
                      <TextFormat type="date" value={contact.contactModifiedAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{contact.company ? <Link to={`company/${contact.company.id}`}>{contact.company.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${contact.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${contact.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${contact.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="dashboardApp.contact.home.notFound">No Contacts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ contact }: IRootState) => ({
  contactList: contact.entities,
  loading: contact.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Contact);
