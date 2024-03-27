import { LitElement, html, css} from 'lit';
import '@vaadin/progress-bar';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column.js';
import '@vaadin/button';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/split-layout';
import '@vaadin/details';

/**
 * This component shows the claims screen
 */
export class TicClaims extends LitElement {

    static styles = css`
    
        :host {
            display: flex;
            gap: 10px;
            width: 100%;
            height: 100%;
            justify-content: space-around;
            flex-direction: column;
            background: var(--lumo-base-color);
            overflow: auto;
        }
        
        .claimsList {
            height: 100%;
            padding: 10px;
        }
    
        .claimsList vaadin-grid{
            height: 100%;
        }
        .claim {
            display: flex;
            width: 100%;
            height: 100%;
            flex-direction: column;
            align-items: center;
        }
    
        .claimHeading {
            display: flex;
            width: 85%;
            justify-content: space-between;
            align-items: center;
        }
    
        .claimSummary {
            background: var(--lumo-tint-5pct);
            width: 85%;
        }
    
        .claimSummaryLeft {
            display: flex;
            flex-direction: column;
            padding-left: 10px;
            padding-right: 10px;
            width: 80%;
        }
    
        .claimSummaryRight {
            width: 20%;
            padding-left: 10px;
            padding-right: 10px;
        }
    
        .claimHeadingLeft {
            display: flex;
            align-items: center;
            gap: 10px;
        }
    
        .claimHeadingRight {
            display: flex;
            align-items: center;
            gap: 10px;
        }
    
        .claim vaadin-details {
            width: 85%;
        }
    
        .preLine {
            white-space: pre-line;
        }
        
        .images {
            width: 100%;
        }
    `;
    
    static properties = {
        _claims: {state: true},
        _selectedClaim: {state: true}
    }

    constructor() {
        super();
        this._claims = null;
        this._selectedClaim = null;
    }

    connectedCallback() {
        super.connectedCallback();
        this._fetchClaims();
    }

    disconnectedCallback() {
        super.disconnectedCallback();
    }

    _fetchClaims(){
        fetch('/api/claim').then(response => {
                                    if (!response.ok) {
                                        throw new Error('Error fetching all claims');
                                    }
                                    return response.json();
                            }).then(data => {
                                this._claims = data;
                            }).catch(error => {
                                throw new Error('Error fetching all claims' + error);
                            });
    }

    render() {
        if(this._selectedClaim){
            return html`${this._renderClaim()}`;
        }else if(this._claims){
            return html`${this._renderClaims()}`;
        }else{
            return html`Loading claims
            <vaadin-progress-bar indeterminate></vaadin-progress-bar>`;
        }
    }

    _renderClaim(){
        return html`
                    
                    <div class="claim">
                        <div class="claimHeading">
                            
                            <div class="claimHeadingLeft">
                                <vaadin-button @click="${this._backToList}">
                                    <vaadin-icon icon="vaadin:list" slot="prefix"></vaadin-icon>
                                    Back
                                </vaadin-button>
                                <h2>Claim ${this._selectedClaim.id}</h2>
                            </div>
                            <div class="claimHeadingRight">
                                <vaadin-button @click="${this._prevClaim}">
                                    <vaadin-icon icon="vaadin:arrow-left" slot="prefix"></vaadin-icon>
                                    Previous  Claim
                                </vaadin-button>
                                <vaadin-button @click="${this._nextClaim}">
                                    Next Claim
                                    <vaadin-icon icon="vaadin:arrow-right" slot="suffix"></vaadin-icon>    
                                </vaadin-button>
                            </div>    
                        </div>
                        <div class="claimSummary">
                            <vaadin-split-layout>
                                <master-content class="claimSummaryLeft">
                                    <h3>Subject</h3>
                                    ${this._selectedClaim.claimSummary.subject}

                                    <h3>Summary</h3>
                                    <div class="preLine">${this._selectedClaim.claimSummary.summary}</div>

                                    <h3>Customer sentiment</h3>
                                    <div class="preLine">${this._selectedClaim.claimSummary.customerSentiment}</div>
                                </master-content>
                                <detail-content class="claimSummaryRight">
                                    <h3>Date and time of event</h3>
                                    <span>${this._selectedClaim.claimSummary.timeOfEvent}</span>

                                    <h3>Location of event</h3>
                                    <span>${this._selectedClaim.claimSummary.locationOfEvent}</span>

                                    <h3>Images</h3>
                                    <img class="images" src="/static/${this._selectedClaim.claimSummary.images[0]}"></img>
                                </detail-content>
                            </vaadin-split-layout>
                        </div>
                        
                        <vaadin-details summary="Original claim content">
                            <div><b>Subject: </b>${this._selectedClaim.claimCustomerInput.subject}</div>
                            <div><b>From: </b>${this._selectedClaim.claimCustomerInput.from}</div>
                            <div><b>Date: </b>${this._selectedClaim.claimCustomerInput.sendTime}</<div>
                            <div class="preLine">
                                ${this._selectedClaim.claimCustomerInput.body}
                            </div>
                        </vaadin-details>
    
                    </div>`;
    }

    _renderClaims(){
        return html`<div class="claimsList">
                        <vaadin-grid 
                                .items="${this._claims}" 
                                .selectedItems="${[this._selectedClaim]}"
                                @active-item-changed="${(e) => {
                                    this._setSelectedClaim(e.detail.value);
                                }}">
                            <vaadin-grid-sort-column path="id" auto-width></vaadin-grid-sort-column>
                            <vaadin-grid-sort-column path="claimSummary.policyNumber" header="Policy Number" auto-width></vaadin-grid-sort-column>
                            <vaadin-grid-sort-column path="claimSummary.subject" header="Subject" auto-width></vaadin-grid-sort-column>
                            <vaadin-grid-sort-column path="claimSummary.timeOfEvent" header="Date" auto-width></vaadin-grid-sort-column>
                        </vaadin-grid>
                    </div>`;
    }

    _setSelectedClaim(selected){
        if(selected){
            this._selectedClaim = selected;
        }else {
            this._selectedClaim = null;
        }
    }

    _addToClaims(item) {
        if (this._claims && this._claims.length > 0) {
            this._claims = [...this._claims, item];
        } else {
            this._claims = [item];
        }
    }

    _backToList(){
        this._selectedClaim = null;
    }

    _nextClaim(){
        let currentIndex = this._selectedClaim.id-1;
        if(currentIndex === this._claims.length-1){
            this._selectedClaim = this._claims[0];
        }else{
            this._selectedClaim = this._claims[currentIndex+1];
        }
    }

    _prevClaim(){
        let currentIndex = this._selectedClaim.id-1;
        if(currentIndex === 0){
            this._selectedClaim = this._claims[this._claims.length-1];
        }else{
            this._selectedClaim = this._claims[currentIndex-1];
        }
    }

}
customElements.define('tic-claims', TicClaims);