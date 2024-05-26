package capstonepj_bkend.bkendcpj.payloads;

public record LikeResponse(
        String id,
        String userId,
        String ticketId,
        String postId,
        String commentId

) {
}
