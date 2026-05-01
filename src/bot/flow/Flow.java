package bot.flow;

public interface Flow {
    String name();
    void run(FlowContext ctx) throws Exception;
}
