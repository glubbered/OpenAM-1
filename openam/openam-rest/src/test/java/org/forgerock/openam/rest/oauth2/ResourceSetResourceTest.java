/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2015 ForgeRock AS.
 */

package org.forgerock.openam.rest.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.forgerock.json.fluent.JsonValue.json;
import static org.forgerock.json.fluent.JsonValue.object;
import static org.forgerock.openam.utils.CollectionUtils.asSet;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;

import org.forgerock.json.fluent.JsonPointer;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.resource.ActionRequest;
import org.forgerock.json.resource.CreateRequest;
import org.forgerock.json.resource.DeleteRequest;
import org.forgerock.json.resource.NotFoundException;
import org.forgerock.json.resource.NotSupportedException;
import org.forgerock.json.resource.PatchRequest;
import org.forgerock.json.resource.QueryFilter;
import org.forgerock.json.resource.QueryRequest;
import org.forgerock.json.resource.QueryResult;
import org.forgerock.json.resource.QueryResultHandler;
import org.forgerock.json.resource.ReadRequest;
import org.forgerock.json.resource.Resource;
import org.forgerock.json.resource.ResourceException;
import org.forgerock.json.resource.ResultHandler;
import org.forgerock.json.resource.ServerContext;
import org.forgerock.json.resource.UpdateRequest;
import org.forgerock.oauth2.resources.ResourceSetDescription;
import org.forgerock.openam.rest.resource.ContextHelper;
import org.forgerock.util.promise.Promise;
import org.forgerock.util.promise.Promises;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResourceSetResourceTest {

    private ResourceSetResource resource;

    private ResourceSetService resourceSetService;
    private ContextHelper contextHelper;

    @BeforeMethod
    public void setup() {
        resourceSetService = mock(ResourceSetService.class);
        contextHelper = mock(ContextHelper.class);

        resource = new ResourceSetResource(resourceSetService, contextHelper);
    }

    @Test
    public void shouldReadResourceSet() throws Exception {

        //Given
        ServerContext context = mock(ServerContext.class);
        ReadRequest request = mock(ReadRequest.class);
        given(request.getFields()).willReturn(Arrays.asList(new JsonPointer("/fred")));
        ResultHandler<Resource> handler = mock(ResultHandler.class);
        ResourceSetDescription resourceSet = new ResourceSetDescription();
        resourceSet.setDescription(json(object()));
        Promise<ResourceSetDescription, ResourceException> resourceSetPromise
                = Promises.newSuccessfulPromise(resourceSet);

        given(contextHelper.getRealm(context)).willReturn("REALM");
        given(contextHelper.getUserId(context)).willReturn("RESOURCE_OWNER_ID");
        given(resourceSetService.getResourceSet(context, "REALM", "RESOURCE_SET_ID", "RESOURCE_OWNER_ID", false))
                .willReturn(resourceSetPromise);

        //When
        resource.readInstance(context, "RESOURCE_SET_ID", request, handler);

        //Then
        verify(handler).handleResult(Matchers.<Resource>anyObject());
    }

    @Test
    public void createShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        CreateRequest request = mock(CreateRequest.class);
        ResultHandler<Resource> handler = mock(ResultHandler.class);

        //When
        resource.createInstance(context, request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void updateShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        UpdateRequest request = mock(UpdateRequest.class);
        ResultHandler<Resource> handler = mock(ResultHandler.class);

        //When
        resource.updateInstance(context, "RESOURCE_SET_UID", request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void deleteShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        DeleteRequest request = mock(DeleteRequest.class);
        ResultHandler<Resource> handler = mock(ResultHandler.class);

        //When
        resource.deleteInstance(context, "RESOURCE_SET_UID", request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void patchShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        PatchRequest request = mock(PatchRequest.class);
        ResultHandler<Resource> handler = mock(ResultHandler.class);

        //When
        resource.patchInstance(context, "RESOURCE_SET_UID", request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void actionInstanceShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        ActionRequest request = mock(ActionRequest.class);
        ResultHandler<JsonValue> handler = mock(ResultHandler.class);

        //When
        resource.actionInstance(context, "RESOURCE_SET_UID", request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void actionCollectionShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        ActionRequest request = mock(ActionRequest.class);
        ResultHandler<JsonValue> handler = mock(ResultHandler.class);

        //When
        resource.actionCollection(context, request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void queryShouldNotBeSupported() {

        //Given
        ServerContext context = mock(ServerContext.class);
        QueryRequest request = mock(QueryRequest.class);
        QueryResultHandler handler = mock(QueryResultHandler.class);
        given(request.getQueryFilter()).willReturn(QueryFilter.equalTo(new JsonPointer("/fred"), 5));

        //When
        resource.queryCollection(context, request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
    }

    @Test
    public void nameQueryShouldBeSupported() throws Exception {

        //Given
        ServerContext context = mock(ServerContext.class);
        QueryRequest request = mock(QueryRequest.class);
        given(request.getFields()).willReturn(Arrays.asList(new JsonPointer("/fred")));
        QueryResultHandler handler = mock(QueryResultHandler.class);
        ResourceSetDescription resourceSet = mock(ResourceSetDescription.class);
        QueryFilter queryFilter = QueryFilter.and(
                QueryFilter.equalTo("/name", "NAME"),
                QueryFilter.equalTo("/resourceServer", "myclient"),
                QueryFilter.equalTo("/policy/permissions/subject", "SUBJECT"));
        Promise<Collection<ResourceSetDescription>, ResourceException> resourceSetsPromise
                = Promises.newSuccessfulPromise((Collection<ResourceSetDescription>) asSet(resourceSet));

        given(contextHelper.getRealm(context)).willReturn("REALM");
        given(contextHelper.getUserId(context)).willReturn("RESOURCE_OWNER_ID");
        given(request.getQueryFilter()).willReturn(queryFilter);
        given(resourceSetService.getResourceSets(eq(context), eq("REALM"),
                Matchers.<ResourceSetWithPolicyQuery>anyObject(), eq("RESOURCE_OWNER_ID"), eq(false))).willReturn(resourceSetsPromise);

        //When
        resource.queryCollection(context, request, handler);

        //Then
        ArgumentCaptor<ResourceSetWithPolicyQuery> queryCaptor
                = ArgumentCaptor.forClass(ResourceSetWithPolicyQuery.class);
        verify(resourceSetService).getResourceSets(eq(context), eq("REALM"), queryCaptor.capture(), eq("RESOURCE_OWNER_ID"), eq(false));
        assertThat(queryCaptor.getValue().getOperator()).isEqualTo(AggregateQuery.Operator.AND);
        assertThat(queryCaptor.getValue().getPolicyQuery())
                .isEqualTo(QueryFilter.equalTo("/permissions/subject", "SUBJECT"));
        assertThat(queryCaptor.getValue().getResourceSetQuery())
                .isEqualTo(org.forgerock.util.query.QueryFilter.and(
                        org.forgerock.util.query.QueryFilter.equalTo("name", "NAME"),
                        org.forgerock.util.query.QueryFilter.equalTo("clientId", "myclient")));
        verify(handler).handleResult(any(QueryResult.class));
    }

    @Test
    public void shouldRevokeAllUserPolicies() {

        //Given
        ServerContext context = mock(ServerContext.class);
        ActionRequest request = mock(ActionRequest.class);
        ResultHandler<JsonValue> handler = mock(ResultHandler.class);

        given(contextHelper.getRealm(context)).willReturn("REALM");
        given(contextHelper.getUserId(context)).willReturn("RESOURCE_OWNER_ID");
        given(request.getAction()).willReturn("revokeAll");
        given(resourceSetService.revokeAllPolicies(context, "REALM", "RESOURCE_OWNER_ID"))
                .willReturn(Promises.<Void, ResourceException>newSuccessfulPromise(null));

        //When
        resource.actionCollection(context, request, handler);

        //Then
        ArgumentCaptor<JsonValue> jsonCaptor = ArgumentCaptor.forClass(JsonValue.class);
        verify(handler).handleResult(jsonCaptor.capture());
        verify(handler, never()).handleError(Matchers.<ResourceException>anyObject());
        assertThat(jsonCaptor.getValue().asMap()).isEmpty();
    }

    @Test
    public void revokeAllUserPoliciesActionShouldHandleResourceException() {

        //Given
        ServerContext context = mock(ServerContext.class);
        ActionRequest request = mock(ActionRequest.class);
        ResultHandler<JsonValue> handler = mock(ResultHandler.class);

        given(contextHelper.getRealm(context)).willReturn("REALM");
        given(contextHelper.getUserId(context)).willReturn("RESOURCE_OWNER_ID");
        given(request.getAction()).willReturn("revokeAll");
        given(resourceSetService.revokeAllPolicies(context, "REALM", "RESOURCE_OWNER_ID"))
                .willReturn(Promises.<Void, ResourceException>newFailedPromise(new NotFoundException()));

        //When
        resource.actionCollection(context, request, handler);

        //Then
        verify(handler).handleError(Matchers.<ResourceException>anyObject());
        verify(handler, never()).handleResult(Matchers.<JsonValue>anyObject());
    }

    @Test
    public void actionCollectionShouldHandleUnsupportedAction() {

        //Given
        ServerContext context = mock(ServerContext.class);
        ActionRequest request = mock(ActionRequest.class);
        ResultHandler<JsonValue> handler = mock(ResultHandler.class);

        given(request.getAction()).willReturn("UNSUPPORTED_ACTION");

        //When
        resource.actionCollection(context, request, handler);

        //Then
        verify(handler).handleError(Matchers.<NotSupportedException>anyObject());
        verify(handler, never()).handleResult(Matchers.<JsonValue>anyObject());
    }
}
