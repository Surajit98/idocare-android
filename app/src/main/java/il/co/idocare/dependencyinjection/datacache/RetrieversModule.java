package il.co.idocare.dependencyinjection.datacache;

import android.content.ContentResolver;

import dagger.Module;
import dagger.Provides;
import il.co.idocare.requests.retrievers.RawRequestRetriever;
import il.co.idocare.requests.retrievers.RequestsRetriever;
import il.co.idocare.useractions.UserActionsToRequestsApplier;
import il.co.idocare.useractions.retrievers.UserActionsRetriever;

@Module
public class RetrieversModule {

    @Provides
    public UserActionsToRequestsApplier userActionsToRequestsApplier() {
        return new UserActionsToRequestsApplier();
    }

    @Provides
    public RawRequestRetriever rawRequestRetriever(ContentResolver contentResolver) {
        return new RawRequestRetriever(contentResolver);
    }

    @Provides
    public UserActionsRetriever userActionsRetriever(ContentResolver contentResolver) {
        return new UserActionsRetriever(contentResolver);
    }

    @Provides
    public RequestsRetriever requestsRetriever(RawRequestRetriever rawRequestRetriever,
                                               UserActionsRetriever userActionsRetriever,
                                               UserActionsToRequestsApplier userActionsToRequestsApplier) {
        return new RequestsRetriever(rawRequestRetriever, userActionsRetriever, userActionsToRequestsApplier);
    }

}